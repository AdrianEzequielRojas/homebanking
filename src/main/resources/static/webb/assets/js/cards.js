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

                    console.log(this.cards)
                    console.log(this.cardColor)
                    console.log(this.cardType)
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

        }
    }
}

).mount('#app')

$(".product-colors span").click(function () {
    $(".product-colors span").removeClass("active");
    $(this).addClass("active");
    $(".active").css("border-color", $(this).attr("data-color-sec"))
    $("body").css("background", $(this).attr("data-color-primary"));
    $(".content h2").css("color", $(this).attr("data-color-sec"));
    $(".content h3").css("color", $(this).attr("data-color-sec"));
    $(".container .imgBx").css("background", $(this).attr("data-color-sec"));
    $(".container .details button").css("background", $(this).attr("data-color-sec"));
    $(".imgBx img").attr('src', $(this).attr("data-pic"));
});


