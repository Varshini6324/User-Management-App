import { useState } from "react";
import {useForm} from "react-hook-form"
import { useNavigate } from "react-router";
 
function AddUser() {

  const {register,handleSubmit,formState:{errors}}=useForm();

  let [loading,setLoading]=useState(false)
  let [error,setError]=useState(null)

  let navigate=useNavigate();

  //form submit
  const onUserCreate=async(newUser)=>{
    // console.log(newUser)
    setLoading(true);

    //make HTTP POST req to create new user
    try{
      let res = await fetch("http://localhost:4000/user-api/users",{
        method: "POST",
        headers: {
          "Content-Type":"application/json",
        },
        body : JSON.stringify(newUser),
      });

      if(res.status===201){
        //user created it should navigate  to user list
        navigate("/users-list")
      }else{
        throw new Error("error occurred")
      }
    }catch(err){
      setError(err);
    }finally{
      setLoading(false);
    }
  };

  if(loading){
    return <p className="text-center text-orange-600 text-3xl">Loading....</p>
  }

  if(error){
    return <p className="text-center text-red-600 text-3xl">{error.message}</p>
  }

  return (
    <div className="text-center">
      <h1 className="text-5xl text-gray-600">Add New User</h1>
      {/* create user form */}
      <form onSubmit={handleSubmit(onUserCreate)} className="max-w-60 mx-auto mt-10">
        <input type="text" {...register("name")} className="mb-5 border w-full" placeholder="Name" />
        <input type="text" {...register("email")} className="mb-10 border w-full" placeholder="email" />
        <input type="date" {...register("dateOfBirth")} className="mb-10 border w-full" placeholder="dateOfBirth" />
        <input type="number" {...register("mobileNumber")} className="mb-10 border w-full" placeholder="mobileNumber" />
        <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded">Add User</button>
      </form>
    </div>
  )
}

export default AddUser