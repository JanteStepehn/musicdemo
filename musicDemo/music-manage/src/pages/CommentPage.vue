<template>
    <div class="table">
        <div class="crumbs">
            <i class="el-icon-tickets">评论信息</i>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
                <el-input v-model="select_word" size="mini" placeholder="筛选关键词" class="handle-input"/>
            </div>
        </div>
        <el-table size="mini" border style="width:100%" height="668px" :data="tableData"  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40"></el-table-column>
            <el-table-column prop="name" label="用户名"  align="center"></el-table-column>
            <el-table-column prop="content" label="评论内容"  align="center"></el-table-column>
            <el-table-column label="操作" width="150" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="danger" @click="handleDelete(scope.row)"> 删除 </el-button> 
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="删除评论" :visible.sync="delVisible" width="300px" center>
            <div align="center">删除不可恢复，是否确定删除？</div>
            <span slot="footer">
                <el-button size="mini" @click="delVisible = false">取消</el-button>
                <el-button size="mini" @click="deleteRow">确定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import {mixin} from '../mixins/index';
import { getUserById,delComment,getComment} from '../api/index';
export default {
    mixins: [mixin],
    props:[ 
        'id',
    ],
    data(){
        return{
            delVisible: false ,//删除
            tableData: [],
            tempData: [],
            select_word: '',
            idx: -1 ,//当前选择项
            multipleSelection: [],
        }
    },
    watch:{
        //搜索
        select_word: function(){
            if(this.select_word == ''){
                this.tableData = this.tempData;
            }else{
                this.tableData = [];
                for(let item of this.tempData){
                    if(item.name.includes(this.select_word)){
                        this.tableData.push(item);
                    }
                }
            }
        }
    },
    created(){
        this.getData();
    },
    methods:{
        //查询所有评论
        getData(){
            this.tempData = []
            this.tableData = []
            getComment(this.$route.query.id).then(response =>{
               for(let item of response){
                   this.getUser(item.userId,item);
               }
            })
        },
        //获取用户对象
        getUser(id,item){
            let _this = this;
            getUserById(id)
            .then(response =>{
                let o = item;
                o.name = response.username;
                this.tempData.push(o);
                this.tableData.push(o);
            })
            .catch(error =>{
                console.log(error);
            })
        },
        //删除
        deleteRow(){
            delComment(this.idx.id)
             .then(response => {
                if(response){
                    this.getData();
                    this.notify("删除成功","success");
                }else{
                    this.notify("删除失败","error")
                }
            })
            .catch(error =>{
                console.log(error);
            });
            this.delVisible = false;
        },
         //批量删除
        delAll(){
        for(let item of this.multipleSelection){
            this.handleDelete(item);
            this.deleteRow();
        }
        this.multipleSelection = [];
        }
    }
}
</script>


<style scoped>
.handle-box{
    margin-bottom: 20px;
}

.handle-input{
    width: 300px;
    display: inline-block;
}

</style>