import axios from "axios"

const REST_API_BASE_URL = 'http://localhost:8080/room/getAll';
export const listRoom = ()=> axios.get(REST_API_BASE_URL);


const REST_API_BUILDING_ALL = 'http://localhost:8080/building/getAll'
export const listBuilding = ()=> axios.get(REST_API_BUILDING_ALL);

const REST_API_BUILDING = 'http://localhost:8080/building'
export const createBuildingService = (building) => axios.post(REST_API_BUILDING,building);
export const deleteBuildingService = (buildingId) => axios.delete(REST_API_BUILDING + '/' + buildingId)
export const get1BuildingService = (buildingId) => axios.get(REST_API_BUILDING + '/' + buildingId)
export const updateBuildingService = (buildingId,building) => axios.put(REST_API_BUILDING +'/'+buildingId ,building);


const REST_API_FLOOR_ALL = 'http://localhost:8080/floor/getAll'
export const listFloor = ()=> axios.get(REST_API_FLOOR_ALL);

const REST_API_FLOOR = 'http://localhost:8080/floor'
export const createFloorService = (floor) => axios.post(REST_API_FLOOR,floor);
export const deleteFloorService = (floorId) => axios.delete(REST_API_FLOOR + '/' + floorId)
export const get1FloorService = (floorId) => axios.get(REST_API_FLOOR + '/' + floorId)
export const updateFloorService = (floorId,floor) => axios.put(REST_API_FLOOR +'/'+floorId ,floor);