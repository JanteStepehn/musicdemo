<template>
    <div class="table">
        <div class="crumbs">
            <i class="el-icon-tickets">歌曲信息</i>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
                <el-input v-model="select_word" size="mini" placeholder="请输入歌曲名" class="handle-input"/>
                <el-button type="primary" size="mini" @click="centerDialogVisible = true">添加歌曲</el-button>
            </div>
        </div>
        <el-table size="mini" border style="width:100%" height="668px" :data="data"  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40"></el-table-column>
            <el-table-column label="歌曲图片" width="110" align="center">
                <template slot-scope="scope">
                    <div class="song-img">
                        <img :src="getUrl(scope.row.pic)" style="width:100%"/>
                    </div>
                    <div class="play" @click="setSongUrl(scope.row.url)">
                        <div v-if="toggle">
                            <svg class="icon">
                                <use xlink:href="#icon-zanting"></use>
                            </svg>
                        </div>
                        <div v-if="!toggle">
                            <svg class="icon">
                                <use xlink:href="#icon-bofanganniu"></use>
                            </svg>
                        </div>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="歌手-歌名" width="120" align="center"></el-table-column>
            <el-table-column prop="introduction" label="专辑" width="150" align="center"></el-table-column>
              <el-table-column label="歌词" align="center">
                    <template slot-scope="scope">
                        <ul style="height: 100px;overflow: scroll;">
                            <li v-for="(item,index) in parseLyric(scope.row.lyric)" :key="index">
                                {{ item }}
                            </li>
                        </ul>
                    </template>
                </el-table-column>
                <el-table-column label="资源更新" align="center" width="100">
                     <template slot-scope="scope">
                        <el-upload :action="uploadUrl(scope.row.id)" :on-success="handleAvatorSuccess" 
                            :before-upload="beforeAvatorUpload">
                            <el-button size="mini"> 更新图片 </el-button>
                        </el-upload>
                        <br/>
                        <el-upload :action="uploadSongUrl(scope.row.id)" :on-success="handleSongSuccess" 
                            :before-upload="beforeSongUpload">
                            <el-button size="mini"> 更新歌曲 </el-button>
                        </el-upload>
                     </template>
                </el-table-column>
            <el-table-column label="操作" width="150" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.row)"> 编辑 </el-button> 
                    <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)"> 删除 </el-button> 
                </template>
            </el-table-column>
        </el-table>

        <div class="pagination" >
            <el-pagination
                background
                layout="total,prev,pager,next"
                :current-page="currentPage"
                :page-size="pageSize"
                :total="tableData.length"
                @current-change="handleCurrentChange"
                >
            </el-pagination>
        </div>
        
        <el-dialog title="添加歌曲" :visible.sync="centerDialogVisible" width="400px" center>
            <el-form :model="registerForm" ref="registerForm" label-width="80px" action="" id="tf">
               <div>
                   <label>歌名</label>
                   <el-input type="text" name="name"/>
               </div>
                <div>
                   <label>专辑</label>
                   <el-input type="text" name="introduction"/>
               </div>
                <div>
                   <label>歌词</label>
                   <el-input type="text" name="lyric"/>
               </div>
                <div>
                   <label>歌曲上传</label>
                    <input type="file" name="file"/>
               </div>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
                <el-button size="mini" @click="addSong">添加</el-button>
            </span>
        </el-dialog>
        
        <el-dialog title="编辑歌曲" :visible.sync="editVisible" width="400px" center>
            <el-form :model="form" ref="form" label-width="80px">
                <el-form-item prop="name" label="歌手-歌名" size="mini">
                    <el-input v-model="form.name" placeholder="歌手-歌名"></el-input>
                </el-form-item>
                <el-form-item prop="introduction" label="专辑" size="mini">
                    <el-input v-model="form.introduction" placeholder="专辑"></el-input>
                </el-form-item>
                 <el-form-item prop="lyric" label="歌词" size="mini">
                    <el-input v-model="form.lyric" placeholder="歌词" type="textarea"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button size="mini" @click="editVisible = false">取消</el-button>
                <el-button size="mini" @click="editSave">确定</el-button>
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
import { mapGetters } from 'vuex';
import '@/assets/js/iconfont.js';
import { selectBySingerId,updateSong,delSong } from '../api/index';
export default {
    mixins: [mixin],
    data(){
        return{
            singerId: '', //歌手ID
            singerName:'', //歌手名
            centerDialogVisible: false, //添加弹窗是否显示
            editVisible: false, //编辑
            delVisible: false ,//删除
            registerForm:{ //添加框
                name: '',
                singerName: '',
                introduction: '',
                lyric: ''
            },
            form:{  //编辑框
                id:'',
                name: '',
                introduction: '',
                lyric:''
            },
            tableData: [],
            tempData: [],
            select_word: '',
            pageSize: 5 ,  //一页显示个数
            currentPage: 1 , //当前页
            idx: -1 ,//当前选择项
            multipleSelection: [],
            toggle: false
        }
    },
    computed:{
         ...mapGetters([
            'isPlay'
        ]),
        //计算当前搜索结果表里的数据
        data(){
            return this.tableData.slice((this.currentPage -1) * this.pageSize,this.currentPage * this.pageSize);
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
        this.singerId = this.$route.query.id;
        this.singerName = this.$route.query.name;
        this.getData();
    },
    destroyed(){
        this.$store.commit('setIsPlay',false);
    },
    methods:{
        //获取当前页
        handleCurrentChange(val){
            this.currentPage = val;
        },
        //查询所有歌曲
        getData(){
            this.tempData = []
            this.tableData = []
            selectBySingerId(this.singerId).then(response =>{
                this.tempData = response;
                this.tableData = response;
                this.currentPage = 1;
            })
        },
        //添加歌曲
        addSong(){
            let _this = this;
            var form = new FormData(document.getElementById('tf'));
            form.append('singerId',this.singerId);
            form.set('name',this.singerName+'-'+form.get('name'));
            if(!form.get('lyric')){
                form.set('lyric','[00:00:00]暂无歌词');
            }
            var req = new XMLHttpRequest();
            req.onreadystatechange = function(){
                if(req.readyState == 4 && req.status ==200){
                    let response = JSON.parse(req.response);
                    if(response.code){
                        _this.getData();
                        _this.registerForm = {};
                        _this.notify(response.msg,'success');
                    }else{
                        _this.notify('保存失败','error');
                    }
                }
            }
            var url = _this.$store.state.HOST+"/song/add";
            req.open('post',url,false);
            req.send(form);
            _this.centerDialogVisible = false;
        },
        //编辑歌手
        handleEdit(row){
            this.editVisible = true;
            this.form = {
                id: row.id,
                name: row.name,
                introduction: row.introduction,
                lyric: row.lyric
            }
        },
        //保存编辑数据
        editSave(){
            let params = new URLSearchParams();
            params.append('id',this.form.id);
            params.append('name',this.form.name);
            params.append('introduction',this.form.introduction);
            params.append('lyric',this.form.lyric);
            updateSong(params)
            .then(response => {
                if(response.code == 1){
                    this.getData();
                    this.notify("修改成功","success");
                }else{
                    this.notify("修改失败","error")
                }
            })
            .catch(error =>{
                console.log(error);
            });
            this.editVisible = false;
        },
        //更新图片
        uploadUrl(id){
            var url = this.$store.state.HOST;
            return url+'/song/updateSongPic?id='+id
        },
        //删除
        deleteRow(){
            delSong(this.idx)
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
        //解析歌词
        parseLyric(text){
            let lines = text.split("\n");
            let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g;
            let result = [];
            for(let item of lines){
                let value = item.replace(pattern,'');
                result.push(value);
            }
            return result;
        },
         //上传歌曲之前的校验
        beforeSongUpload(file){
            var testMsg = file.name.substring(file.name.lastIndexOf('.')+1);
            if(testMsg!='mp3'&& testMsg!='ncm'){
                this.$message({
                    message: '上传文件只能是mp3和ncm格式',
                    type: 'error'
                });
                return false;
            }
            return true;
        },
        //上传歌曲成功
        handleSongSuccess(response){
            let _this = this;
            if(response.code == 1){
            _this.getData();
            _this.$notify({
                title: '上传成功',
                type: 'success'
                });
            }else{
            _this.$notify({
                title: '上传失败',
                type: 'error'
                });
            }
        },
        //更新歌曲url
        uploadSongUrl(id){
            var url = this.$store.state.HOST;
            return url+'/song/updateSongUrl?id='+id
        },
        // 切换播放歌曲
        setSongUrl(url){
            this.toggle = !this.toggle;
            this.$store.commit('setUrl',this.$store.state.HOST+url);
            if(this.isPlay){
                this.$store.commit('setIsPlay',false);
            }else{
                this.$store.commit('setIsPlay',true);
            }
        }
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