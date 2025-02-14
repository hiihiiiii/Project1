import React, { useEffect, useState } from 'react'
import SideBarComponent from '../../SideBarComponent'
import { createFloorService, updateFloorService, get1FloorService, listBuilding } from '../../../../service/RoomService';
import { useNavigate, useParams } from 'react-router-dom';

const AddAndUpdateFloor = () => {
  const [floorName, setFloorName] = useState('');
  const [buildings, setBuildings] = useState([]);
  const [selectedBuildingId, setSelectedBuildingId] = useState('');
  const [generatedFloorId, setGeneratedFloorId] = useState('');
  const [selectedBuilding, setSelectedBuilding] = useState(null);
  const { floorId } = useParams();
  const navigator = useNavigate();

  // Hàm tạo floorId tự động dựa trên buildingId
  const generateFloorId = (buildingId) => {
    if (!buildingId) return '';
    
    // Lấy danh sách tầng hiện có của tòa nhà này
    const existingFloors = buildings.find(b => b.buildingId === buildingId)?.floors || [];
    
    // Tìm số tầng cao nhất hiện tại
    let maxFloorNumber = 0;
    existingFloors.forEach(floor => {
      const floorNum = parseInt(floor.floorId.replace(/[^0-9]/g, ''));
      if (floorNum > maxFloorNumber) {
        maxFloorNumber = floorNum;
      }
    });

    // Tạo ID mới với format: FL + buildingId + số tầng tiếp theo
    const nextFloorNumber = maxFloorNumber + 1;
    const newFloorId = `FL${buildingId}${nextFloorNumber.toString().padStart(2, '0')}`;
    
    return newFloorId;
  };

  useEffect(() => {
    getAllBuilding();

    if (floorId) {
      get1FloorService(floorId).then((response) => {
        const floorData = response.data.data;
        setFloorName(floorData.floorName);
        setSelectedBuilding(floorData.building);
      }).catch(error => {
        console.error(error);
      });
    }
  }, [floorId]);

  function getAllBuilding() {
    listBuilding().then((response) => {
      setBuildings(response.data.data);
    }).catch(error => {
      console.error(error);
    });
  }

  const handleBuildingChange = (event) => {
    const selectedBuildingId = event.target.value;
    const building = buildings.find(b => b.buildingId === selectedBuildingId);
    setSelectedBuilding(building);
    if (!floorId) { // Chỉ tạo ID mới khi đang thêm mới tầng
      const newFloorId = generateFloorId(selectedBuildingId);
      setGeneratedFloorId(newFloorId);
    }
  };

  function saveOrUpdateFloor(e) {
    e.preventDefault();
    const floor = {
      floorId: floorId || generatedFloorId, //chi tao khi tao moi floor 
      floorName,
      building: {
        createAt: selectedBuilding.createAt || new Date().toISOString(),
        updateAt: new Date().toISOString(),
        createBy: selectedBuilding.createBy || "system",
        updateBy: "system",
        buildingId: selectedBuilding.buildingId,
        buildingName: selectedBuilding.buildingName,
        buildingDescription: selectedBuilding.buildingDescription || "",
        buildingGender: selectedBuilding.buildingGender || true
      }
    }
    console.log(floor);
    if (floorId) {
      updateFloorService(floorId, floor).then((response) => {
        console.log(response.data);
        navigator('/floor');
      }).catch(error => {
        console.error(error);
      });
    } else {
      createFloorService(floor).then((response) => {
        console.log('tao');
        console.log(response.data);
        navigator('/floor');
      }).catch(error => {
        console.error(error);
      });
    }
  }

  function pageTitle() {
    if (floorId) {
      return <h2>Cập nhật thông tin tầng</h2>;
    }
    return <h2>Thêm tầng</h2>;
  }

  return (
    <div>
      <SideBarComponent />
      <div className='container'>
        {pageTitle()}
        <form className="row g-3 needs-validation" noValidate>
          <div className="col-md-5">
            <label htmlFor="validationCustom02" className="form-label">Tầng</label>
            <input
              type="text"
              className="form-control"
              id="validationCustom02"
              name='floorname'
              value={floorName}
              onChange={e => setFloorName(e.target.value)}
              required
            />
            <div className="valid-feedback">
              Looks good!
            </div>
          </div>

          <div className="col-md-3">
            <label htmlFor="buildingSelect" className="form-label">Tòa nhà</label>
            <select
              className="form-select"
              id="buildingSelect"
              value={selectedBuilding?.buildingId || ''}
              onChange={handleBuildingChange}
              required
            >
              <option value="">Chọn tòa nhà</option>
              {Array.isArray(buildings) && buildings.map((building) => (
                <option key={building.buildingId} value={building.buildingId}>
                  {building.buildingName}
                </option>
              ))}
            </select>
            <div className="invalid-feedback">
              Vui lòng chọn một tòa nhà.
            </div>
          </div>
          {!floorId && generatedFloorId && (
            <div className="col-md-5">
              <label className="form-label">Floor ID (tự động)</label>
              <input
                type="text"
                className="form-control"
                value={generatedFloorId}
                readOnly
                disabled
              />
            </div>
          )}
          <div className="col-12">
          <button 
              className="btn btn-primary" 
              type="submit" 
              onClick={saveOrUpdateFloor}
            >
              Submit form
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddAndUpdateFloor;