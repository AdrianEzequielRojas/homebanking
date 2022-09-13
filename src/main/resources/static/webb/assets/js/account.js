const { createApp } = Vue

createApp({
    data() {
        return {
            clients: [],
            transactions: [],
            accounts: [],
            queryString: "",
            params: "",
            id: "",

        }
    },
    created() {
        this.queryString = location.search;
        this.params = new URLSearchParams(this.queryString);
        this.id = this.params.get('id');
        axios.get("/api/accounts/" + this.id)
            .then(res => {
                this.accounts = res.data;
                this.transactions = this.accounts.transaction;
                console.log(this.transactions)
            }
            )
    },

    methods: {},
}
)

    .mount('#app')

