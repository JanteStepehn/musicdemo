const user = {
    state:{
        userId: '',
        userName: '',
        avator: '',
    },
    getters: {
        userId: state => {
            let userId = state.userId;
            if(!userId){
                userId = JSON.parse(window.sessionStorage.getItem('userId'))
            }
            return userId;
        },
        userName: state => {
            let userName = state.userName;
            if(!userName){
                userName = JSON.parse(window.sessionStorage.getItem('userName'))
            }
            return userName;
        },
        avator: state => {
            let avator = state.avator;
            if(!avator){
                avator = JSON.parse(window.sessionStorage.getItem('avator'))
            }
            return avator;
        }
    },

    mutations: {
        setUserId: (state,userId) => {
            state.userId = userId
            window.sessionStorage.setItem('userId',JSON.stringify(userId))
        },
        setUserName: (state,userName) => {
            state.userName = userName
            window.sessionStorage.setItem('userName',JSON.stringify(userName))
        },
        setAvator: (state,avator) => {
            state.avator = avator
            window.sessionStorage.setItem('avator',JSON.stringify(avator))
        },
    }
}

export default user