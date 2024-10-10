import React,{useEffect, useState} from 'react'
import SideBarComponent from '../../SideBarComponent'
import { listBuilding } from '../../../../service/RoomService'
import { useNavigate,useParams } from 'react-router-dom'
import { deleteBuildingService } from '../../../../service/RoomService'


const MainBuilding = () => {

    const [buildings,setBuildings] = useState([])
    const navigator  = useNavigate();


    useEffect(() => {
        getAllBuilding();

      }, [])

      function getAllBuilding(){
        listBuilding().then((response)=>{
          setBuildings(response.data.data);
        }).catch(error =>{
          console.error(error);
        }
        )
      }
      const getGenderText = (gender) => {
        return gender ? 'Nam' : 'Nữ';
      }
      function addNewBuilding(){
        navigator('/add-building')
      }
      function updateBuilding(buildingId){
        navigator(`/update-building/${buildingId}` ,{ state: { buildingId } })
      }

      const deleteBuilding = (buildingId) => {  
        const confirmDelete = window.confirm("Bạn có chắc chắn muốn xóa tòa nhà này?");  
        if (confirmDelete) {  
            deleteBuildingService(buildingId).then((response) => {  
                getAllBuilding();  
            }).catch(error => {  
                console.error(error);  
            });  
        }
  }
  return (
    <div>
        <SideBarComponent/>
        <div className='container'>
      <h2>Danh sách tòa nhà </h2>
      <button className='btn btn-primary mb-2' onClick={addNewBuilding}>Tạo mới tòa </button>
      <table className='table table-striped table-bordered'>
        <thead>
          <tr>
              <th>Mã tòa nhà </th>
              <th>Tên tòa nhà </th>
              <th>Mô tả </th>
              <th>Giới tính người ở </th>
              <th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {
            buildings.map(building =>
              <tr key={building.buildingId}>
                <td>{building.buildingId}</td>
                <td>{building.buildingName}</td>
                <td>{building.buildingDescription}</td>
                <td>{getGenderText(building.buildingGender)}</td>
                <td>
                  <button className='btn btn-info' onClick={()=>updateBuilding(building.buildingId)}>Cập nhật</button>
                  <button className='btn btn-danger' onClick={()=>deleteBuilding(building.buildingId)}>Xóa</button>
                </td>
              </tr>
            )
          }
        </tbody>
      </table>
    </div>
        
        </div>
  )
}

export default MainBuilding