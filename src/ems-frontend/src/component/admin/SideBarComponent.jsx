import React from 'react'
import './sidebar.css';
import { useNavigate } from 'react-router-dom'

const SideBarComponent = () => {
    const navigator  = useNavigate();

    function student(){
        navigator('/student')
      }


      function room(){
        navigator('/room')
      }
      function building(){
        navigator('/building')
      }
      function floor(){
        navigator('/floor')
      }

      function request(){
        navigator('/request')
      }
      function report(){
        navigator('/report')
      }
      function pay(){
        navigator('/pay')
      }
      function facilities(){
        navigator('/facilities')
      }
      function employee(){
        navigator('/employee')
      }
      function contract(){
        navigator('/contract')
      }
      function account(){
        navigator('/account')
      }
      function login(){
        navigator('/login')
      }
  return (
    <div className="custom-sidebar">
            <div className="sidebar-toggle">
                <i className="fas fa-bars"></i>
            </div>
            <div className="user-info">
                <i className="fas fa-user"></i>
                <span>Nguyễn Thanh Thảo</span>
            </div>
            <nav className="sidebar-nav">
                <ul>
                    <li><a href="" onClick={student}><i className="fas fa-users" ></i>Quản lý sinh viên</a></li>
                    <li className="dropdown">
                      <a href="" onClick={room}><i className="fas fa-door-open"></i>Quản lý phòng</a>
                      <ul className="dropdown-menu">
                        <li><a href="" onClick={building}><i className="fas fa-plus"></i>Tòa nhà</a></li>
                        <li><a href="" onClick={floor}><i className="fas fa-edit"></i>Tầng</a></li>
                        <li><a href=""onClick={room}><i className="fas fa-search"></i>Phòng </a></li>
                      </ul>
                    </li>
                    <li><a href="" onClick={contract}><i className="fas fa-file-contract"></i>Quản lý hợp đồng</a></li>
                    <li><a href="" onClick={pay}><i className="fas fa-money-bill"></i>Quản lý thanh toán</a></li>
                    <li><a href="" onClick={facilities}><i className="fas fa-tasks"></i>Quản lý CSVC</a></li>
                    <li><a href="" onClick={employee}><i className="fas fa-user-tie"></i>Quản lý nhân viên</a></li>
                    <li><a href="" onClick={account}><i className="fas fa-user-cog"></i>Quản lý tài khoản</a></li>
                    <li><a href="" onClick={report}><i className="fas fa-chart-bar"></i>Báo cáo</a></li>
                    <li><a href="" onClick={request}><i className="fas fa-question-circle"></i>Yêu cầu</a></li>
                    <li><a href="" onClick={login}><i className="fas fa-sign-out-alt"></i>Đăng xuất</a></li>
                </ul>
            </nav>
        </div>
  )
}

export default SideBarComponent