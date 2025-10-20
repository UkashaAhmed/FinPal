import { useEffect, useState } from 'react'
import { api } from '../api'

type Account = { id: string, name: string }
type Tx = { id:string, date:string, merchant:string, description:string, amountCents:number, type:string, category?:string, confidence?:number }
type ImportResult = { total:number, created:number, categorized:number, message:string }

export default function Transactions() {
  const [accounts, setAccounts] = useState<Account[]>([])
  const [selected, setSelected] = useState<string>('')
  const [file, setFile] = useState<File | null>(null)
  const [rows, setRows] = useState<Tx[]>([])
  const [from, setFrom] = useState<string>(() => new Date(Date.now()-120*86400000).toISOString().slice(0,10))
  const [to, setTo] = useState<string>(() => new Date().toISOString().slice(0,10))
  const [busy, setBusy] = useState<boolean>(false)
  const [result, setResult] = useState<ImportResult | null>(null)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    api.get('/accounts').then(r => {
      setAccounts(r.data)
      if (r.data?.length) setSelected(r.data[0].id)
    }).catch(e => {
      console.error('Failed to load accounts', e)
      setError('Failed to load accounts')
    })
  }, [])

  const load = () => {
    if (!selected) return
    api.get('/transactions', { params: { accountId: selected, from, to, size: 100 } })
      .then(r => setRows(r.data.content))
      .catch(e => {
        console.error('Failed to load transactions', e)
        setError('Failed to load transactions')
      })
  }

  useEffect(() => { if (selected) load() }, [selected, from, to])

  const onImport = async () => {
    setError(null); setResult(null)
    if (!file) { setError('Please choose a CSV file first.'); return }
    setBusy(true)
    try {
      const fd = new FormData(); fd.append('file', file)
      const res = await api.post('/transactions/import', fd, { headers: { 'Content-Type': 'multipart/form-data' } })
      setResult(res.data as ImportResult)
      setFile(null)
      load()
    } catch (e:any) {
      console.error('Import failed:', e)
      setError(e?.response?.data?.message || 'Import failed. Check console.')
    } finally { setBusy(false) }
  }

  const setRange = (days:number) => {
    setFrom(new Date(Date.now()-days*86400000).toISOString().slice(0,10))
    setTo(new Date().toISOString().slice(0,10))
  }

  return (
    <div className="space-y">
      <h1 className="h1">Transactions</h1>

      {error && <div className="notice error">{error}</div>}
      {result && (
        <div className="notice info">
          Imported: <strong>{result.created}/{result.total}</strong> • Auto-categorized: <strong>{result.categorized}</strong> • {result.message}
        </div>
      )}

      <div className="card pad">
        <div className="controls">
          <label>Account
            <select className="select" style={{marginLeft: 8}} value={selected} onChange={e=>setSelected(e.target.value)}>
              {accounts.map(a => <option key={a.id} value={a.id}>{a.name}</option>)}
            </select>
          </label>

          <label>From
            <input className="input" style={{marginLeft:8}} type="date" value={from} onChange={e=>setFrom(e.target.value)} />
          </label>

          <label>To
            <input className="input" style={{marginLeft:8}} type="date" value={to} onChange={e=>setTo(e.target.value)} />
          </label>

          <input className="input" type="file" accept=".csv" onChange={e=>setFile(e.target.files?.[0] ?? null)} />
          <button className="btn" onClick={onImport} disabled={!file || busy}>{busy ? 'Importing…' : 'Import CSV'}</button>

          <div className="spacer" />
          <div className="caption">Quick Range:</div>
          <button className="btn-ghost" onClick={()=>setRange(30)}>30d</button>
          <button className="btn-ghost" onClick={()=>setRange(120)}>120d</button>
          <button className="btn-ghost" onClick={()=>{ setFrom('2025-09-01'); setTo('2025-09-30') }}>Sept 2025</button>
        </div>
      </div>

      <div className="card table-wrap">
        <table className="table">
          <thead>
            <tr>
              <th>Date</th>
              <th>Merchant</th>
              <th>Description</th>
              <th>Type</th>
              <th className="right">Amount</th>
              <th>Category</th>
              <th>Conf.</th>
            </tr>
          </thead>
          <tbody>
            {rows.map(r => (
              <tr key={r.id}>
                <td>{r.date}</td>
                <td>{r.merchant}</td>
                <td>{r.description}</td>
                <td>{r.type}</td>
                <td className="right">${(r.amountCents/100).toFixed(2)}</td>
                <td>{r.category || '-'}</td>
                <td>{r.confidence ? Math.round(r.confidence*100) + '%' : '-'}</td>
              </tr>
            ))}
            {!rows.length && (
              <tr>
                <td colSpan={7} style={{ textAlign:'center', padding: '18px', color: 'var(--muted)' }}>
                  No transactions for the selected range.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  )
}
