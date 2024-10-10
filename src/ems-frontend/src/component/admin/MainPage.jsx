import React from 'react'
import SideBarComponent from './SideBarComponent'

const MainPage = () => {
  
  return (
    <>
    <div className="app">
      
        {/* Main content container */}
        <main className="ml-64 pt-16 min-h-screen pb-[footer-height]">
          <div className="p-4">
            <SideBarComponent/>
          </div>
        </main>

       </div>
    </>
  )
}

export default MainPage