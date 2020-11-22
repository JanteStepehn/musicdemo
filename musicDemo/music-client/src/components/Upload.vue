<template>
    <div class="upload">
        <p class="title">修改头像</p>
        <hr/>
        <div class="section">
            <el-upload drag :action="uploadUrl()" :show-file-list="false" :on-success="handleAvatorSuccess"
                :before-upload="beforeAvatorUpload">
                <i class="el-icon-upload"></i>
                <div>
                    将文件拖到此处，或<span style="color:blue">修改头像</span>
                </div>
                <div slot="tip">只能上传jpg/png文件，不能超过10MB</div>
            </el-upload>
        </div>
    </div>
</template>

<script>
import {mapGetters} from 'vuex'
import {mixin} from '../mixins'
export default {
    name: 'upload',
    mixins: [mixin],
    computed:{
        ...mapGetters([
            'userId'
        ])
    },
    methods:{
        uploadUrl(){
            var url = this.$store.state.configure.HOST;
            return url+'/consumer/updateConsumerPic?id='+this.userId;
        },
        handleAvatorSuccess(response){
            if(response.code == 1){
                this.$store.commit('setAvator',response.avator);
                this.notify('修改成功','success');
            }else{
                this.notify('修改失败','error')
            }
        },
        beforeAvatorUpload(file){
            const isJPG = (file.type === 'image/jpg')||(file.type === 'image/png') ||(file.type === 'image/jpeg');
            if(!isJPG){
                this.$message.error('上传图片只能是jpg或png');
                return false;
            }
            const isLt10M = (file.size / 1024 /1024) < 10;
            if(!isLt10M){
                this.$message.error('上传图片不大于10MB');
                return false;
            }
                return true;
            }
    }
}
</script>


<style lang="scss" scoped>
@import '../assets/css/upload.scss';
</style>