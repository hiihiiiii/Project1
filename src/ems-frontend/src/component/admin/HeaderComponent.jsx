import React from 'react'
import './stylehf.css'
const HeaderComponent = () => {
  return (
    <div>
      <header className="header">
        <div className="wrapper">
            <div className="left-side">
                <div className="logo">
                    <img src="https://cdn.haitrieu.com/wp-content/uploads/2022/03/Logo-Dai-Hoc-Giao-Thong-Van-Tai-UTC.png" alt="University Logo" />
                </div>
                 <div className="title">
                    <h2>TRƯỜNG ĐẠI HỌC GIAO THÔNG VẬN TẢI</h2>
                    <small>UNIVERSITY OF TRANSPORT AND COMMUNICATIONS</small>
                </div>
            </div>
            <div className="right-side">
            <h1 >QUẢN LÝ KÍ TÚC XÁ</h1>
            </div>
        </div>
      </header>
    </div>
  )
}

export default HeaderComponent