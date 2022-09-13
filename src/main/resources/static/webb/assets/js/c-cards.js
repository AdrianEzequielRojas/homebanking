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
                .then(() =>
                    Swal.fire(
                        'Good job!',
                        'You clicked the button!',
                        'success'
                    ))
                .then(() => window.location.href = '/webb/pages/cards.html')

                .catch(error => Swal.fire({
                    icon: 'error',
                    text: `${error.response.data}`
                }))
        }
    }
}


).mount('#app')


$(document).ready(function () {
    // Color Picker Tool Js
    const themeSwitchers = document.querySelectorAll("input");
    const dynamicInputs = document.querySelectorAll("input.input-color-picker");

    const handleThemeUpdate = (cssVars) => {
        const root = document.querySelector(":root");
        const keys = Object.keys(cssVars);
        keys.forEach((key) => {
            root.style.setProperty(key, cssVars[key]);
        });
    };

    themeSwitchers.forEach((item) => {
        item.addEventListener("click", (e) => {
            const bgColor = e.target.getAttribute("data-bg-color");
            const color = e.target.getAttribute("data-color");
            handleThemeUpdate({
                "--primary-bg-color": bgColor,
            });
            console.log(bgColor, color);

            $("input.input-color-picker[data-id='bg-color']").val(bgColor);
        });
    });

    dynamicInputs.forEach((item) => {
        item.addEventListener("input", (e) => {
            const cssPropName = `--primary-${e.target.getAttribute("data-id")}`;
            console.log(cssPropName);
            handleThemeUpdate({
                [cssPropName]: e.target.value
            });
        });
    });
});

let previousCreditCardClass = "none";
let creditCardEl = document.querySelector(".visual-credit-card");
$('input[name="cardtype"]').on("change", function () {
    let newCreditCardClass = "cc-" + $('input[name="cardtype"]:checked').val();
    creditCardEl.classList.remove(previousCreditCardClass);
    creditCardEl.classList.add(newCreditCardClass);
    previousCreditCardClass = newCreditCardClass;
});
