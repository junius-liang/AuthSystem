function setStorage(key:any,obj:any) {
    window.localStorage.setItem(key,JSON.stringify(obj));
}
function getStorage(key:any){
    const strObj=window.localStorage.getItem(key);
    return JSON.parse(typeof strObj === "string" ? strObj :'');
}
export { setStorage,getStorage };
