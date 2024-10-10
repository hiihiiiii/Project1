import './App.css'
import FooterComponent from './component/admin/FooterComponent'
import HeaderComponent from './component/admin/HeaderComponent'
import MainPage from './component/admin/MainPage'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Login from './Login'
import AddStudent from './component/admin/managing/student/AddStudent'
import MainStudent from './component/admin/managing/student/MainStudent'
import MainRoom from './component/admin/managing/room/MainRoom'
import MainRequest from './component/admin/managing/request/MainRequest'
import MainReport from './component/admin/managing/report/MainReport'
import MainPay from './component/admin/managing/pay/MainPay'
import MainFacilities from './component/admin/managing/facilities/MainFacilities'
import MainEmployee from './component/admin/managing/employee/MainEmployee'
import MainAccount from './component/admin/managing/account/MainAccount'
import MainContract from './component/admin/managing/contract/MainContract'
import MainBuilding from './component/admin/managing/building/MainBuilding'
import MainFloor from './component/admin/managing/floor/MainFloor'
import AddBuilding from './component/admin/managing/building/AddBuilding'
import AddAndUpdateFloor from './component/admin/managing/floor/AddAndUpdateFloor'

function App() {

  return (
    <>
    <BrowserRouter>
      <HeaderComponent/>
        <Routes>
          <Route path='/' element={ <Login/>}></Route>
          <Route path='/login' element={<Login/>}></Route>
          <Route path='/add-student' element={<AddStudent/>}></Route>
          <Route path='/student' element={ <MainStudent/>}></Route>

          <Route path='/room' element={ <MainRoom/>}></Route>
          <Route path='/building' element={ <MainBuilding/>}></Route>
          <Route path='/add-building' element={ <AddBuilding/>}></Route>
          <Route path='/update-building/:buildingId' element={ <AddBuilding/>}></Route>

          <Route path='/floor' element={ <MainFloor/>}></Route>
          <Route path='/add-floor' element={ <AddAndUpdateFloor/>}></Route>

          <Route path='/request' element={ <MainRequest/>}></Route>
          <Route path='/report' element={ <MainReport/>}></Route>
          <Route path='/pay' element={ <MainPay/>}></Route>
          <Route path='/facilities' element={ <MainFacilities/>}></Route>
          <Route path='/employee' element={ <MainEmployee/>}></Route>
          <Route path='/account' element={ <MainAccount/>}></Route>
          <Route path='/contract' element={ <MainContract/>}></Route>
        </Routes>
      <FooterComponent/>
    </BrowserRouter>
    </>
  )
}

export default App
