import React,{useEffect, useState} from 'react'
import SideBarComponent from '../../SideBarComponent'
import { createBuildingService,updateBuildingService,get1BuildingService } from '../../../../service/RoomService';
import { useNavigate,useParams } from 'react-router-dom';

const AddBuilding = () => {
  const [buildingName, setBuildingName]=useState('');
  const [buildingDescription, setBuildingDes]=useState('');
  const [buildingGender, setGenderBuil]=useState('');

  const {buildingId}= useParams();
  const navigator = useNavigate();

  useEffect(()=>{
    if(buildingId){
      get1BuildingService(buildingId).then((response)=>{
        setBuildingName(response.data.data.buildingName);
          setBuildingDes(response.data.data.buildingDescription);  
          setGenderBuil(response.data.data.buildingGender ? 1 : 0);
      }).catch(error =>{
        console.error(error);
      })
    }
  },[buildingId])

  function saveOrUpdateBuilding(e){
    e.preventDefault();

    const building = {buildingId,buildingName,buildingDescription,buildingGender}
    console.log(building);
    if(buildingId){
      console.log(building);
      updateBuildingService(buildingId,building).then((response)=>{
        console.log(response.data)
        navigator('/building')
      }).catch(error =>{
        console.error(error);
      })
    }else{
      createBuildingService(building).then((response) =>{
        console.log(response.data)
        navigator('/building')
      }).catch(error =>{
        console.error(error);
      })
    }
  }
  function pageTitle(){
    if(buildingId){
      return <h2>Cập nhật thông tin tòa</h2>
    }else{
      <h2>Thêm tòa</h2>
    }
  }
  return (
    <div>
      <SideBarComponent/>
      <div className='container'>
        {
          pageTitle()
        }
      <form className="row g-3 needs-validation" noValidate>
        <div className="col-md-5">
          <label htmlFor="validationCustom02" className="form-label">Tên tòa nhà</label>
          <input type="text" className="form-control" id="validationCustom02" name='buildingname' value={buildingName} onChange={e => setBuildingName(e.target.value)} required/>
          <div className="valid-feedback">
            Looks good!
          </div>
        </div>
        <div className="col-md-16">
          <label htmlFor="validationCustom02" className="form-label">Mô tả</label>
          <input type="text" className="form-control" id="validationCustom02" name='buildingdes' value={buildingDescription} onChange={e => setBuildingDes(e.target.value)} required/>
          <div className="valid-feedback">
            Looks good!
          </div>
        </div>
        <div className="col-md-3">
          <label htmlFor="validationCustom04" className="form-label">Giới tính người ở</label>
          <select className="form-select" id="validationCustom04" value={buildingGender === 1 ? "nam" : "nu"}  onChange={e => setGenderBuil(e.target.value === "nam" ? 1 : 0)} required>
            <option value='nam'>Nam </option>
            <option value='nu'>Nữ</option>
          </select>
          <div className="invalid-feedback">
            Please select a valid state.
          </div>
        </div>
        
        <div className="col-12">
          <button className="btn btn-primary" type="submit" onClick={saveOrUpdateBuilding}>Submit form</button>
        </div>
      </form>
      </div>
        
    </div>
  )
}

export default AddBuilding