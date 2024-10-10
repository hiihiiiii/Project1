import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import SideBarComponent from '../../SideBarComponent';
import { listRoom } from '../../../../service/RoomService'

const MainRoom = () => {
  const [rooms, setRooms] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    listRoom().then((response) => {
      setRooms(response.data.data);
    }).catch(error => {
      console.error(error);
    });
  }, []);

  function addNewRoom() {
    navigate('/add-room');
  }

  return (
    <div>
      <SideBarComponent />
      <div className='container'>
        <h2>Danh sách phòng</h2>
        <button className='btn btn-primary mb-2' onClick={addNewRoom}>
          Tạo mới phòng
        </button>
        <table className='table table-striped table-bordered'>
          <thead>
            <tr>
              <th>Mã phòng</th>
              <th>Tên phòng</th>
              <th>Mô tả phòng</th>
              <th>Loại phòng</th>
              <th>Số tầng</th>
              <th>Người tối đa</th>
              <th>Số phòng</th>
              <th>Trạng thái phòng</th>
            </tr>
          </thead>
          <tbody>
            {rooms.map(room => (
              <tr key={room.roomId}>
                <td>{room.roomId}</td>
                <td>{room.roomName}</td>
                <td>{room.roomDes}</td>
                <td>{room.roomType.roomTypeName}</td>
                <td>{room.floor.floorName}</td>
                <td>{room.maxNumber}</td>
                <td>{room.roomNumber}</td>
                <td>{room.roomStatus}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default MainRoom