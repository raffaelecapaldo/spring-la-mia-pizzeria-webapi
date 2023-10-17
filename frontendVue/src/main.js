import 'bootstrap/dist/css/bootstrap.css'
import "vue-toastification/dist/index.css"
import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import Toast from "vue-toastification";

import App from './App.vue'
/* import router from './router'
 */
const app = createApp(App)

app.use(createPinia())
app.use(Toast)
// app.use(router)

app.mount('#app')
