import Sidebar from "@/layout/sidebar/Sidebar.tsx";
import {Outlet} from "react-router-dom";

export default function MainPage() {
    return (
      <div>
          <Sidebar/>

          <Outlet/>
      </div>
    );
}