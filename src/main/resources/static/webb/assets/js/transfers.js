const { createApp } = Vue

createApp({
    data() {
        return {
            clients: {},
            currentClient: {},
            accounts: [],
            loans: [],
            cards: [],
            accountsClients: [],
            queryString: "",
            params: "",
            id: "",

            cardColor: "",
            cardType: "",

            myAccount: "",

            amount: 0,
            numberFrom: "",
            numberTo: "",
            description: "",

            anotherAccount: "",
        }
    },
    created() {
        this.loadClientList();



    },
    methods: {
        loadClientList() {
            axios.get('/api/clients/current')
                .then(res => {
                    this.clients = res.data;
                    this.accounts = this.clients.accounts;
                    this.loans = this.clients.loans;
                    this.cards = this.clients.cards;


                    this.cardColor = this.cards.color;
                    this.cardType = this.cards.type;
                    this.normalizeDate(this.cards)

                }
                )
        }, normalizeDate(card) {
            card.forEach(card => {
                card.fromDate = card.fromDate.slice(2, 7)
                card.thruDate = card.thruDate.slice(2, 7)
            });
        }, createCards() {
            axios.post('/api/clients/current/cards', `cardColor=${this.cardColor}&cardType=${this.cardType}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(() => window.location.href = '/webb/pages/cards.html')

        }, createTransactions() {
            axios.post("/api/transactions", `amount=${this.amount}&description=${this.description}&numberFrom=${this.numberFrom}&numberTo=${this.numberTo}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(() =>
                    Swal.fire(
                        'Good job!',
                        'You clicked the button!',
                        'success'
                    ))
                .then(() => window.location.href = '/webb/pages/transactions.html')
                .catch(error => Swal.fire({
                    icon: 'error',
                    text: `${error.response.data}`
                }))
        }
    }
}
).mount('#app')