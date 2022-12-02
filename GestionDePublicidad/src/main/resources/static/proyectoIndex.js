const menu_btn = document.querySelector("#menu-btn")
const sidebar = document.querySelector("#sidebar")
const container = document.querySelector(".container-datos")
const list = document.querySelectorAll(".list")

menu_btn.addEventListener("click", ()=>{
    sidebar.classList.toggle("active-nav")
    container.classList.toggle("active-cont")
})

function activarLink(){
    list.forEach((item)=>
    item.classList.remove("active"))
    this.classList.add("active")
}

list.forEach((item)=>
item.addEventListener("click", activarLink)
)