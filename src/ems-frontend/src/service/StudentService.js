import axios from "axios"

const REST_API_BASE_URL = 'http://localhost:8080/student/getAll';

export const listStudent = ()=> axios.get(REST_API_BASE_URL);
