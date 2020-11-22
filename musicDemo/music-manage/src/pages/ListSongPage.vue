<template>
    <div class="table">
        <div class="crumbs">
            <i class="el-icon-tickets">歌单歌曲信息</i>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
                <el-input v-model="select_word" size="mini" placeholder="请输入歌曲名" class="handle-input"/>
                <el-button type="primary" size="mini" @click="centerDialogVisible = true">添加歌曲</el-button>
            </div>
        </div>
        <el-table size="mini" border style="width:100%" height="668px" :data="tableData"  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40"></el-table-column>
            <el-table-column prop="name" label="歌手-歌名"  align="center"></el-table-column>
            <el-table-column label="操作" width="150" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)"> 删除 </el-button> 
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="添加歌曲" :visible.sync="centerDialogVisible" width="400px" center>
            <el-form :model="registerForm" ref="registerForm" label-width="80px" action="" id="tf">
                <el-form-item prop="singerName" label="歌手名字" size="mini">
                    <el-input v-model="registerForm.singerName" placeholder="歌手名字"></el-input>
                </el-form-item>
                <el-form-item prop="songName" label="歌曲名字" size="mini">
                    <el-input v-model="registerForm.songName" placeholder="歌曲名字"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
                <el-button size="mini" @click="getSongId">添加</el-button>
            </span>
        </el-dialog>
        
        <el-dialog title="删除歌曲" :visible.sync="delVisible" width="300px" center>
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
import { listSongDetail,  selectBySongId, selectByName, delListSong, addListSong } from '../api/index';
export default {
    mixins: [mixin],
    data(){
        return{
            centerDialogVisible: false, //添加弹窗是否显示
            delVisible: false ,//删除
            registerForm:{ //添加框
              singerName: '',
              songName: ''
            },
            tableData: [],
            tempData: [],
            select_word: '',
            idx: -1 ,//当前选择项
            multipleSelection: [],
            songListId: '' //歌单ID
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
        this.songListId = this.$route.query.id;
        this.getData();
    },
    methods:{
        //查询所有歌曲
        getData(){
            this.tempData = []
            this.tableData = []
            listSongDetail(this.songListId).then(response =>{
               for(let item of response){
                   this.getSong(item.songId);
               }
            })
        },
        //根据歌曲ID查询歌曲
        getSong(id){
            selectBySongId(id)
            .then(response =>{
                this.tempData.push(response);
                this.tableData.push(response);
            })
            .catch(error =>{
                console.log(error);
            })
        },
        //  添加歌曲前  获取歌曲ID
        getSongId(){
            let _this = this;
            var name = _this.registerForm.singerName+'-'+_this.registerForm.songName;
            selectByName(name)
            .then(response => {
                var id = response[0].id;
                if(response!=null && response.length != 0){
                    _this.addSong(id);
                }else{
                    this.notify("歌手或者歌名不存在","error");
                    _this.centerDialogVisible = false;
                }
            })
        },
        //添加歌曲
        addSong(songId){
            let _this = this;
            let params = new URLSearchParams();
            params.append('songId',songId);
            params.append('songListId',this.songListId);
            addListSong(params)
            .then(response => {
                if(response.code == 1){
                    this.getData();
                    this.notify("添加成功","success");
                }else{
                    this.notify("添加失败","error")
                }
            })
            .catch(error =>{
                console.log(error);
            });
            _this.centerDialogVisible = false;
        },
        //删除
        deleteRow(){
            let _this = this;
            let params = new URLSearchParams();
            params.append('songId',_this.idx);
            params.append('songListId',_this.songListId);
            delListSong(params)
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
            _this.delVisible = false;
        },
    }
}
</script>


<style scoped>
.handle-box{
    margin-bottom: 20px;
}
.song-img{
    width: 100%;
    height: 80px;
    border-radius: 5px;
    margin-bottom: 5px;
    overflow: hidden;
}
.handle-input{
    width: 300px;
    display: inline-block;
}
.pagination{
    display: flex;
    justify-content: center;
}
.play{
    position: absolute;
    z-index: 100;
    width: 80px;
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    top: 18px;
    left: 15px;
}
.icon {
    width: 2em;
    height: 2em;
    color: white;
    fill: currentColor;
    overflow: hidden;
}
</style>