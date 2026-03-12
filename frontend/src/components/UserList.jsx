import { useEffect, useState } from "react"
import { useNavigate } from "react-router";

function UserList() {

  let [loading,setLoading]=useState(false)
  let [error,setError]=useState(null)
  let [users,setUsers]=useState([])
  let navigate=useNavigate();

  useEffect(()=>{
      async function getUsers() {
        setLoading(true);
        try{
          let res=await fetch("http://localhost:4000/user-api/users",{
          method: "GET"
          });

          if(res.status === 200){
            //extract json data
            let data = await res.json();
            //update the state 
            setUsers(data.payload)
          } else{
              throw new Error("error occurred")
            } 
        }catch(err){
          setError(err);
        }finally{
          setLoading(false);
        }
      }
      getUsers();
  },[])
    

  //go to user
  const gotoUser=(userObj)=>{
    navigate("/user", { state: userObj })
  }

  

  return (
    <div>
      <h1 className="text-5xl text-gray-600">List of Users</h1>
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-10">
        {
          users.map(userObj=><div key={userObj.email} onClick={() => gotoUser(userObj)} className="p-10 shadow-2xl cursor-pointer">
            <p className="text-3xl">{userObj.name}</p>
            <p className="text-2xl">{userObj.email}</p>
          </div>
          )
        }
      </div>
    </div>
  )
}

export default UserList