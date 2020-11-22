<template>
<div>
    <loginLogo/>
    <div class="signUp">
        <div class="signUp-head">
            <span>登录</span>
        </div>
        <el-form :model="loginForm" ref="loginForm" label-width="70px" class="demo-ruleForm" :rules="rules">
            <el-form-item prop="userName" label="用户名">
                <el-input v-model="loginForm.userName" placeholder="用户名"></el-input>
            </el-form-item>
            <el-form-item prop="passWord" label="密码">
                <el-input type="passWord" v-model="loginForm.passWord" placeholder="密码"></el-input>
            </el-form-item>
          
            <div class="login-btn">
                <el-button @click="goSignUp">注册</el-button>
                <el-button @click="handleLoginIn" type="primary">登录</el-button>
            </div>
        </el-form>
    </div>

</div>
</template>

<script>
import {mixin} from '../mixins'
import {loginIn} from '../api/index'
import loginLogo from '../components/LoginLogo'
export default {
    name: 'login-in',
    mixins: [mixin],
    components: {
        loginLogo ,
    },
    data() {
        return {
            loginForm: {
                userName: '',
                passWord: '',
            },
            rules: {
                userName: [
                    {required: true,trigger: 'blur',message: '请输入用户名'}
                ],
                passWord: [
                    {required: true,trigger: 'blur',message: '请输入密码'}
                ]
            }
        }
    },
    mounted(){
        this.changeIndex('登录');    
    },
    methods: {
        handleLoginIn(){
            let _this = this;
            let params = new URLSearchParams();
            params.append('userName',this.loginForm.userName);
            params.append('passWord',this.loginForm.passWord);
            loginIn(params)
                .then(response =>{
                    if(response.code == 1){
                        _this.notify('登录成功','success');
                        _this.$store.commit('setLoginIn',true);
                        _this.$store.commit('setUserId',response.userMsg.id);
                        _this.$store.commit('setUserName',response.userMsg.userName);
                        _this.$store.commit('setAvator',response.userMsg.avator);
                        setTimeout(function(){
                            _this.changeIndex('首页');
                            _this.$router.push({path: '/'});
                        },0);
                    }else{
                        _this.notify('用户名或密码错误','error');
                    }
                })
                .catch(error => {
                    _this.notify('用户名或密码错误','error');
                })
        },
        goSignUp(){
            this.changeIndex('注册');
            this.$router.push({path: '/sign-up'});
        },
        changeIndex(val){
            this.$store.commit('setActiveName',val);
        }
    }
}
</script>


<style lang="scss" scoped>
@import '../assets/css/sign-up.scss';
</style>