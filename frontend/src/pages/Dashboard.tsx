import { useEffect, useState } from 'react'
import { api } from '../api'
import { LineChart, Line, XAxis, YAxis, Tooltip, ResponsiveContainer, CartesianGrid } from 'recharts'

type Account = { id: string, name: string, allowanceCents: number, period: string }

export default function Dashboard() {
  const [accounts, setAccounts] = useState<Account[]>([])
  useEffect(() => { api.get('/accounts').then(r => setAccounts(r.data)) }, [])

  // Placeholder trend data
  const data = [
    { d: '09-01', s: 5 }, { d: '09-05', s: 12 }, { d: '09-10', s: 25 },
    { d: '09-15', s: 28 }, { d: '09-20', s: 38 }, { d: '09-25', s: 45 }
  ]

  return (
    <div className="space-y">
      <div className="grid-3">
        <div className="card pad">
          <div className="muted">Allowance</div>
          <div className="h1" style={{fontSize: '28px', marginTop: 6}}>
            {accounts.length ? `$${(accounts[0].allowanceCents/100).toFixed(2)}` : '-'}
          </div>
          <div className="caption">{accounts[0]?.period ?? ''}</div>
        </div>

        <div className="card pad">
          <div className="muted">This Month Spend</div>
          <div className="h1" style={{fontSize: '28px', marginTop: 6}}>$92.83</div>
          <div className="caption">Across 4 categories</div>
        </div>

        <div className="card pad">
          <div className="muted">Streak</div>
          <div className="h1" style={{fontSize: '28px', marginTop: 6}}>3 days</div>
          <div className="caption">Kept under snack budget</div>
        </div>
      </div>

      <div className="card pad">
        <div className="row" style={{marginBottom: 10}}>
          <h2 className="h2">Spending Trend</h2>
          <span className="badge">Last 30 days</span>
        </div>
        <div style={{height: 260}}>
          <ResponsiveContainer width="100%" height="100%">
            <LineChart data={data}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="d" />
              <YAxis />
              <Tooltip />
              <Line type="monotone" dataKey="s" stroke="#6366F1" strokeWidth={3} dot={false} />
            </LineChart>
          </ResponsiveContainer>
        </div>
      </div>
    </div>
  )
}
