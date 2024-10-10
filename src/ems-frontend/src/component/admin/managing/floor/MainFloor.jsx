import React,{useEffect, useState} from 'react'
import SideBarComponent from '../../SideBarComponent'
import { listFloor } from '../../../../service/RoomService'
import { useNavigate,useParams } from 'react-router-dom'
import { deleteFloorService } from '../../../../service/RoomService'


const MainFloor = () => {

    const [floors,setFloors] = useState([])
    const navigator  = useNavigate();


    useEffect(() => {
      getAllFloor();

      }, [])

      function getAllFloor(){
        listFloor().then((response)=>{
          setFloors(response.data.data);
        }).catch(error =>{
          console.error(error);
        }
        )
      }
      
      
      function addNewFloor(){
        navigator('/add-floor')
      }
      function updateFloor(floorId){
        navigator(`/update-floor/${floorId}` ,{ state: { floorId } })
      }

      const deleteFloor = (floorId) => {  
        const confirmDelete = window.confirm("Bạn có chắc chắn muốn xóa tòa nhà này?");  
        if (confirmDelete) {  
            deleteFloorService(floorId).then((response) => {  
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
      <h2>Danh sách tầng </h2>
      <button className='btn btn-primary mb-2' onClick={addNewFloor}>Tạo mới </button>
      <table className='table table-striped table-bordered'>
        <thead>
          <tr>
              <th className='col-md-3'>Mã tầng </th>
              <th>Tên tầng </th>
              <th>Tòa </th>
              <th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {
            floors.map(floor =>
              <tr key={floor.floorId}>
                <td>{floor.floorId}</td>
                <td>{floor.floorName}</td>
                <td>{floor.building.buildingName}</td>
                <td>
                  <button className='btn btn-info' onClick={()=>updateFloor(floor.floorId)}>Cập nhật</button>
                  <button className='btn btn-danger' onClick={()=>deleteFloor(floor.floorId)}>Xóa</button>
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

export default MainFloor