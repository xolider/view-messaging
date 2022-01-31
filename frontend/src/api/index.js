import axios from 'axios'

const url = "http://localhost:8081"
axios.defaults.baseURL = url

const api = {
    postUser: async(username) => {
        return await axios.post('/user', {
            username: username
        })
    },
    getOnlineUsers: async() => {
        return await axios.get('/users/online')
    },
    getMessages: async(firstuser, seconduser) => {
        return await axios.get('/messages/' + firstuser + '/' + seconduser)
    }
}

export default api