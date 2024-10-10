import React,{useEffect, useState} from 'react'
import { listStudent } from '../../../../service/StudentService'
import { useNavigate } from 'react-router-dom'
import SideBarComponent from '../../SideBarComponent'
const MainStudent = () => {
  const [students, setStudents] = useState([])

  const navigator  = useNavigate();

  useEffect(() => {
    listStudent().then((response)=>{
      setStudents(response.data.data);
    }).catch(error =>{
      console.error(error);
    }
    )
  }, [])

  function addNewStudent(){
    navigator('/add-student')
  }
  return (
    <div>
      <SideBarComponent/>
      <div className='container'>
      <h2>Danh sanh sinh vien</h2>
      <button className='btn btn-primary mb-2' onClick={addNewStudent}>Tạo mới sinh viên </button>
      <table className='table table-striped table-bordered'>
        <thead>
          <tr>
              <th>Mã sinh viên </th>
              <th>Họ và tên </th>
              <th>Ngày sinh </th>
              <th>Địa chỉ </th>
              <th>Giới tính </th>
              <th>Identification</th>
              <th>Số điện thoại </th>
              <th>Số điện thoại người thân </th>
              <th>Email </th>
              <th>Ưu tiên </th>
          </tr>
        </thead>
        <tbody>
          {
            students.map(student =>
              <tr key={student.studentId}>
                <td>{student.studentId}</td>
                <td>{student.fullname}</td>
                <td>{student.dateOfBirth}</td>
                <td>{student.studentAddress}</td>
                <td>{student.studentGender}</td>
                <td>{student.studentIdentification}</td>
                <td>{student.phoneNumber}</td>
                <td>{student.relativesPhone}</td>
                <td>{student.studentEmail}</td>
                <td>{student.studentPriority}</td>
              </tr>
            )
          }
        </tbody>
      </table>
    </div>
    </div>
    
    
  )
}

export default MainStudent