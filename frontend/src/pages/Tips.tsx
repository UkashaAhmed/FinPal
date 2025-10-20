import { useEffect, useState } from 'react'
import { api } from '../api'

type Tip = { text:string, reason:string }

export default function Tips() {
  const [tips, setTips] = useState<Tip[]>([])
  useEffect(()=>{ api.get('/ai/tips?limit=5').then(r=>setTips(r.data)) },[])

  return (
    <div className="space-y">
      <h1 className="h1">Smart Tips</h1>
      <p className="muted">Bite-sized ideas to help you stick to your goals.</p>

      <div className="grid-3">
        {tips.map((t, i) => (
          <div key={i} className="card pad">
            <div style={{fontSize: 28, marginBottom: 8}}>💡</div>
            <div style={{fontWeight: 700}}>{t.text}</div>
            <div className="caption" style={{marginTop: 6}}>{t.reason}</div>
            <div style={{marginTop: 12, display: 'flex', gap: 8}}>
              <button className="btn-ghost">👍 Helpful</button>
              <button className="btn-ghost">👎 Not now</button>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}
