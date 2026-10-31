const express = require('express');
const cors = require('cors');
const sqlite3 = require('sqlite3').verbose();
const jwt = require('jsonwebtoken');

const app = express();
app.use(express.json());
app.use(cors());

// Configuración de la Base de Datos (Se crea sola en el archivo notas.db)
const db = new sqlite3.Database('./notas.db');
db.run(`CREATE TABLE IF NOT EXISTS notes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER,
    title TEXT,
    content TEXT,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP
)`);

// Clave secreta para el token
const SECRET_KEY = "clave_secreta_davinci";

// 1. Endpoint de Login
app.post('/auth/login', (req, res) => {
    const { email, password } = req.body;
    
    // Validación simple
    if (email === 'admin@test.com' && password === '123456') {
        const token = jwt.sign({ id: 1, email: email }, SECRET_KEY);
        return res.json({
            token: token,
            user: { id: 1, name: "Zeth", email: email }
        });
    } else {
        return res.status(401).json({ message: "Credenciales incorrectas" });
    }
});

// Middleware para validar el token
function validateToken(req, res, next) {
    const authHeader = req.headers['authorization'];
    if (!authHeader) return res.status(401).json({ message: "No autorizado" });

    const token = authHeader.split(' ')[1];
    jwt.verify(token, SECRET_KEY, (err, user) => {
        if (err) return res.status(401).json({ message: "Token inválido" });
        req.user = user;
        next();
    });
}

// 2. Endpoint para Crear Nota
app.post('/notes', validateToken, (req, res) => {
    const { title, content } = req.body;
    const userId = req.user.id;

    db.run(`INSERT INTO notes (user_id, title, content) VALUES (?, ?, ?)`, 
    [userId, title, content], function(err) {
        if (err) return res.status(500).json({ message: "Error al guardar" });
        
        res.status(201).json({
            id: this.lastID,
            title: title,
            content: content,
            createdAt: new Date().toISOString()
        });
    });
});

// 3. Endpoint para Listar Notas
app.get('/notes', validateToken, (req, res) => {
    db.all(`SELECT * FROM notes WHERE user_id = ? ORDER BY createdAt DESC`, 
    [req.user.id], (err, rows) => {
        if (err) return res.status(500).json({ message: "Error al obtener notas" });
        res.json(rows);
    });
});

// Ruta de prueba (Para verificar desde el navegador del celular)
app.get('/', (req, res) => {
    res.send('¡El backend funciona correctamente!');
});

// Iniciar el servidor
const PORT = 3000;
// '0.0.0.0' permite que otros dispositivos en la red Wi-Fi se conecten
app.listen(PORT, '0.0.0.0', () => {
    console.log(`Servidor backend corriendo en http://0.0.0.0:${PORT}`);
});