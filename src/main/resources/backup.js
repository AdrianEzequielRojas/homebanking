const { createApp } = Vue

createApp({
    data() {
        return {
            clients: [],
            nameValue: '',
            lastNameValue: '',
            emailValue: '',
        }
    },
    created() {

        this.loadData()
    },


    methods: {
        loadData() {
            axios.get('/clients')
                .then((res) => {
                    this.clients = res.data._embedded.clients;
                }
                )
        }, addClients() {
            axios.post('/clients', {
                name: this.nameValue,
                lastName: this.lastNameValue,
                email: this.emailValue,
            })
                .then((res) => {
                    this.clients.push(res.data)
                    console.log(res.data)
                })
        }
    }
}).mount('#app')