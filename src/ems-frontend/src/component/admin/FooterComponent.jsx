import React from 'react'
import './stylehf.css'

const FooterComponent = () => {
  return (
    <div>
    <footer className="footer">
        <div className="wrapper">
          <div className="left-side">
            <p >Bản quyền thuộc Trường Đại học Giao thông vận tải<br/>
            Địa chỉ: Số 3 đường Cầu Giấy, phường Láng Thượng, quận Đống Đa, Hà Nội<br/>
            Điện thoại: 02437663131 - Fax: 02437668913 <br/>
            Email: dhtvtt@utc.edu.vn
            </p>
          </div>
          <div className="right-side">
            <p>Theo dõi UTC</p>
            <div className="icon">
            </div>
          </div>
        </div>
      </footer>
    </div>
  )
}

export default FooterComponent