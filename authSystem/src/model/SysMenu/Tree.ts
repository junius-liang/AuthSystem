export interface Tree {
    component: string
    id:string,
    name:string,
    parentId:number,
    path:string,
    select:boolean,
    sortValue:number,
    status:number,
    children?: Tree[]
}
