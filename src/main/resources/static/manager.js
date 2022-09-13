const { createApp } = Vue

createApp({
    data() {
        return {
            clients: [],
            nameValue: '',
            lastNameValue: '',
            emailValue: '',
            phoneValue: '',
        }
    },
    created() {
        this.loadData()
    },


    methods: {
        loadData() {
            axios.get('/api/clients')
                .then((res) => {
                    this.clients = res.data;
                    this.accounts = res.data.accounts;
                    this.loans = res.data.loans;
                    
                    console.log(this.accounts);
                }
                )
        }, addClients() {
            axios.post('/rest/clients', {
                name: this.nameValue,
                lastName: this.lastNameValue,
                email: this.emailValue,
                phone: this.phoneValue,
            })
                .then((res) => {
                    this.clients.push(res.data)
                    console.log(res.data)
                })
        }, deleteClients(clientSelected) {
            axios.delete("/rest/clients/" + clientSelected.id)
                .then(res => {
                    this.loadData()
                })
        }, editClients(clientSelected) {
            let newEmail
            newEmail = prompt("New Email")
            client = {
                email: newEmail,
            }
            axios.patch("/rest/clients/" + clientSelected.id, client)
                .then(() => this.loadData())
                
        },/*  loadClientList(clientSelected) {
            axios.get('/api/clients/' + this.id)
                .then(res => {
                    this.clients = res.data;
                    this.accountsClients = cli}entSelected.accounts;
                    console.log(this.clients)
                }
                )
        } */
    }
}).mount('#app')