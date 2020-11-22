<template>
<div>
    <div class="info">
        <div class="title">
            <span>编辑个人资料</span>
        </div>
        <hr/>
        <div class="personal">
            <el-form :model="registerForm" ref="registerForm" label-width="70px" class="demo-ruleForm" :rules="rules">
            <el-form-item prop="userName" label="用户名">
                <el-input v-model="registerForm.userName" placeholder="用户名"></el-input>
            </el-form-item>
            <el-form-item prop="passWord" label="密码">
                <el-input type="passWord" v-model="registerForm.passWord" placeholder="密码"></el-input>
            </el-form-item>
            <el-form-item prop="sex" label="性别">
                <el-radio-group v-model="registerForm.sex">
                    <el-radio :label="1">男</el-radio> 
                    <el-radio :label="0">女</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item prop="phoneNum" label="手机">
                <el-input v-model="registerForm.phoneNum" placeholder="手机"></el-input>
            </el-form-item>
            <el-form-item prop="email" label="邮箱">
                <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
            </el-form-item>
            <el-form-item prop="birth" label="生日">
                <el-date-picker :editable="false" v-model="registerForm.birth" placeholder="选择日期" style="width: 100%;"></el-date-picker>
            </el-form-item>
            <el-form-item prop="introduction" label="签名">
                <el-input v-model="registerForm.introduction" placeholder="签名"></el-input>
            </el-form-item>
            <el-form-item prop="location" label="地区">
                <el-select style="width:100%;" v-model="registerForm.location" placeholder="地区">
                    <el-option v-for="item in cities" :key="item.value" :label="item.label" :value="item.value"></el-option> 
                </el-select>
            </el-form-item>
        </el-form>
        <div class="btn">
                <div @click="saveMsg" type="primary">保存</div>
                <div @click="goback(-1)">取消</div>
            </div>
        </div>
        
    </div>

</div>
</template>

<script>
import {mapGetters} from 'vuex'
import {mixin} from '../mixins'
import { getUserById,updateUserMsg} from '../api/index';
import loginLogo from '../components/LoginLogo'
import {rules,cities} from '../assets/data/form'
export default {
    name: 'info',
    mixins: [mixin],
    data() {
        return {
            registerForm: {
                userName: '',
                passWord: '',
                sex: '',
                phoneNum: '',
                email: '',
                birth: '',
                introduction: '',
                location: '',
            },
            cities: [],
            rules: {}
        }
    },
    computed:{
        ...mapGetters([
            'userId',
        ])
    },  
    created() {
        this.rules = rules;
        this.cities = cities;
    },
    mounted(){
        this.getMsg(this.userId);
    },
    methods: {
        getMsg(userId){
            getUserById(userId)
                .then(res => {
                   this.registerForm.userName =res.username;
                   this.registerForm.passWord =res.password;
                   this.registerForm.sex =res.sex;
                   this.registerForm.phoneNum =res.phoneNum;
                   this.registerForm.email =res.email;
                   this.registerForm.birth =res.birth;
                   this.registerForm.introduction =res.introduction;
                   this.registerForm.location =res.location;
                })
                .catch(error => {
                    console.log(error);
                })
        },
        saveMsg(){
            let _this = this;
            let d = new Date(this.registerForm.birth);
            let datetime =d.getFullYear() + '-' +(d.getMonth() + 1) + '-' + d.getDate();
            let params = new URLSearchParams();
            params.append('id',this.userId);
            params.append('userName',this.registerForm.userName);
            params.append('passWord',this.registerForm.passWord);
            params.append('sex',this.registerForm.sex);
            params.append('email',this.registerForm.email);
            params.append('birth',datetime);
            params.append('introduction',this.registerForm.introduction);
            params.append('location',this.registerForm.location);
            params.append('avator','/img/user.jpg');
            updateUserMsg(params)
                .then(response =>{
                    if(response.code == 1){
                        this.$store.commit('setUserName',this.registerForm.userName);
                        _this.notify('修改成功','success');
                        setTimeout(function(){
                            _this.changeIndex('首页');
                            _this.$router.push({path: '/'});
                        },2000);
                    }else{
                        _this.notify('修改失败','error');
                    }
                })
                .catch(error => {
                    _this.notify('修改失败','error');
                })
        },
        goback(index){
            this.$router.go(index);
        },
        changeIndex(val){
            this.$store.commit('setActiveName',val);
        }
    }
}
</script>


<style lang="scss" scoped>
@import '../assets/css/info.scss';
</style>