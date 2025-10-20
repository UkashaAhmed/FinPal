import { NavLink, Route, Routes } from 'react-router-dom'
import Dashboard from './pages/Dashboard'
import Transactions from './pages/Transactions'
import Tips from './pages/Tips'

const NavItem = ({ to, label, icon }: {to: string, label: string, icon: string}) => (
  <NavLink
    to={to}
    className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`}
  >
    <span className="icon">{icon}</span>
    <span>{label}</span>
  </NavLink>
)

export default function App() {
  return (
    <div className="app">
      {/* Sidebar */}
      <aside className="sidebar">
        <div className="brand">FinPal</div>
        <nav className="nav">
          <NavItem to="/" label="Dashboard" icon="📊" />
          <NavItem to="/transactions" label="Transactions" icon="🧾" />
          <NavItem to="/tips" label="Tips" icon="💡" />
        </nav>
        <div className="caption" style={{marginTop:14}}>AI Teen Finance Coach</div>
      </aside>

      {/* Main */}
      <main className="main">
        <header className="topbar">
          <div className="topbar-inner">
            <div className="brand" style={{display: 'block', margin: 0}}>FinPal</div>
            <div>
              <span className="badge">Demo</span>
              <span className="badge" style={{marginLeft:8}}>v0.1</span>
            </div>
          </div>
        </header>

        <div className="container">
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/transactions" element={<Transactions />} />
            <Route path="/tips" element={<Tips />} />
          </Routes>
        </div>
      </main>
    </div>
  )
}
