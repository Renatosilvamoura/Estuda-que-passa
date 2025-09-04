
from flask import Flask, render_template, request

app = Flask(__name__)

# Rota inicial → index.html
@app.route('/')
def index():
    return render_template('index.html')

# Rota de login → login.html
@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        email = request.form['email']
        senha = request.form['senha']
        # Aqui você pode validar usuário e senha
        return f"Login realizado para: {email}"
    return render_template('login.html')

# Rota de cadastro → cadastro.html
@app.route('/cadastro', methods=['GET', 'POST'])
def cadastro():
    if request.method == 'POST':
        nome = request.form['nome']
        email = request.form['email']
        senha = request.form['senha']
        endereco = request.form['endereco']
        latitude = request.form['latitude']
        longitude = request.form['longitude']
        # Aqui você poderia salvar no banco de dados
        return f"Usuário {nome} cadastrado com sucesso!"
    return render_template('cadastro.html')

if __name__ == '__main__':
    app.run(debug=True)
