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
              <a @click="startDeletePizza(pizza)" class="btn btn-danger delete-button"><i class="fa-regular fa-trash-can"></i></a>
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

  <!-- Modale -->
  <div v-if="eliminating" class="modal fade show" tabindex="-1" style="display: block;" aria-modal="true" role="dialog">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5">Eliminazione pizza</h1>
        <button @click="eliminating = false" type="button" class="btn-close"></button>
      </div>
      <div class="modal-body">
        Cancellare la pizza <b>{{ pizzaToDelete.name }}</b> con ID <b>{{ pizzaToDelete.id }}</b>?
      </div>
      <div class="modal-footer">
        <button @click="deletePizza(pizzaToDelete.id)" class="btn btn-danger">Cancella</button>
      </div>
    </div>
  </div>
</div>
<div v-if="eliminating" class="modal-backdrop fade show"></div>
</template>

<script>
import axios from 'axios';
import { useToast } from "vue-toastification";

export default {
  data() {
    return {
      STORAGE: "http://localhost:8080",
      API_URL: "http://localhost:8080/api/v1/pizzas",
      pizzas: null,
      queryName: "",
      noPizzas: false,
      eliminating: false,
      pizzaToDelete: null,
      toast: useToast()
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

    },
    startDeletePizza(pizza) {
        this.eliminating = true
        this.pizzaToDelete = pizza
    },
    deletePizza(id) {
      axios.delete(this.API_URL + "/" + id)
      .then((res) => {
        this.toast.success("Pizza eliminata!", {
        timeout: 2000
      });
        this.eliminating = false
        this.pizzaToDelete = null
        this.getPizzas();
      })
      .catch((e) => {
        console.log(e)
      })

    }
  },
  mounted() {
    this.getPizzas()
  }
}
</script>

<style lang="scss" scoped></style>