import { FormEvent, useState } from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function LoginPage() {
  const { login, isAuthenticated } = useAuth();
  const [email, setEmail] = useState('admin@nexastock.dev');
  const [password, setPassword] = useState('Admin123!');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  if (isAuthenticated) {
    return <Navigate to="/" replace />;
  }

  async function handleSubmit(event: FormEvent) {
    event.preventDefault();
    setError('');
    setLoading(true);
    try {
      await login(email, password);
    } catch {
      setError('No se ha podido iniciar sesión. Revisa el email y la contraseña.');
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="login-page">
      <section className="login-panel">
        <p className="eyebrow">Full Stack Portfolio</p>
        <h1>NexaStock</h1>
        <p>
          Plataforma SaaS de inventario y pedidos construida con Spring Boot, PostgreSQL,
          React, TypeScript y JWT.
        </p>

        <form onSubmit={handleSubmit} className="form-card">
          <label>
            Email
            <input value={email} onChange={(e) => setEmail(e.target.value)} />
          </label>
          <label>
            Contraseña
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
          </label>
          {error && <p className="error">{error}</p>}
          <button disabled={loading}>{loading ? 'Entrando...' : 'Entrar'}</button>
        </form>

        <div className="demo-users">
          <strong>Usuarios demo</strong>
          <span>ADMIN: admin@nexastock.dev / Admin123!</span>
          <span>MANAGER: manager@nexastock.dev / Manager123!</span>
        </div>
      </section>
    </div>
  );
}
