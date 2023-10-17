<template>
  <header>
    <div class="position-relative">
      <h1 class="text-yellow text-center">Gestione pizze</h1>
      <a class="btn btn-warning position-absolute" id="add-button" >Aggiungi una pizza</a>
    </div>
  </header>
  <main>
    <div class="d-flex justify-content-center">
      <form @submit.prevent="getPizzas()" id="search">
        <div class="input-group mb-3">
          <input name="name" type="text" v-model="queryName" class="form-control" placeholder="Trova una pizza">
          <button class="btn btn-danger" type="submit" >Cerca</button>
        </div>
      </form>
    </div>
    <div v-if="!noPizzas" class="row">
      <div v-for="pizza in pizzas" class="col-xl-3 col-lg-4 col-md-6 col-sm-6 d-flex justify-content-center mb-3">
        <div class="card" style="width: 18rem;">
          <img
            :src="pizza.imageUrl != null ? STORAGE + pizza.imageUrl : 'https://rejse-glæde.dk/wp-content/themes/klikko3_b_18_07_2023/dest/images/no-image.png'"
            class="card-img-top" :alt="pizza.name">
          <div class="card-body">
            <h5 class="card-title">{{ "ID: " + pizza.id }}</h5>
            <h4 class="card-title">{{ pizza.name }}</h4>
            <p class="card-text">{{ pizza.description }}</p>
            <p class="card-text">{{ "Prezzo: " + pizza.apiPrice.toFixed(2).replace('.', ',') + "&euro;" }}</p>
            <a class="btn btn-info me-2"><i class="fa-solid fa-pencil"></i></a>
            <form class="delete-form" method="POST">
              <a class="btn btn-danger delete-button"><i class="fa-regular fa-trash-can"></i></a>
            </form>
          </div>
        </div>
      </div>
    </div>
    <div v-else class="d-flex justify-content-center">
    <div class="alert alert-warning" role="alert">
      Pizze non trovate
</div>
</div>
  </main>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      STORAGE: "http://localhost:8080",
      API_URL: "http://localhost:8080/api/v1/pizzas",
      pizzas: null,
      queryName: "",
      noPizzas: false
    }
  },
  methods: {
    getPizzas() {
      axios.get(this.API_URL, {params: 
      {q: this.queryName}
    })
        .then((res) => {
          this.noPizzas = false
          this.pizzas = res.data
        })
        .catch((e) => {
          this.noPizzas = true
        })

    }
  },
  mounted() {
    this.getPizzas()
  }
}
</script>

<style lang="scss" scoped></style>