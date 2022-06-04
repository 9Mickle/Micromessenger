<template>
  <div v-if="this.$store.getters.isAuthenticated">
    <div v-for="(user, index) in users" :key="user.id">
      <b-card :class="[index % 2 === 0 ? 'bg-dark text-light' : 'bg-light']">
        <div style="width: 50%; float: left">
          <img class="myIcon"
               src="./assets/avatar.png" alt="user-avatar"/>
          <span class="myText">{{ user.username }} </span>
        </div>
        <div style="width: 50%; float: left">
          <div style="width: 50%; float: left">
            <router-link to="/profile">
              <b-button class="ms-auto myButton bg-primary" @click="saveUsernameInLocaleStorage(user.username)"
                        v-if="!(user.username === currentUsername)">
                Профиль
              </b-button>
            </router-link>
          </div>
          <div style="width: 50%; float: left" v-if="role === 'admin'">
            <b-button class="ms-auto myButton bg-warning">Редактировать</b-button>
            <b-button v-if="!(user.username === currentUsername)" class="ms-auto myButton bg-danger"
                      @click="deleteUser(user.username)">Удалить
            </b-button>
          </div>
        </div>
      </b-card>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

let token = localStorage.getItem('token');
const config = {
  headers: {Authorization: "Bearer " + token}
};
// axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}
export default {
  name: 'usersGet',
  data() {
    return {
      users: [],
      role: '',
      currentUsername: '',
      user: Object,
    }
  },
  mounted() {
    this.usersGet()
    this.getUserRole()
    this.currentUsername = localStorage.getItem('loginUsername')
  },
  methods: {
    usersGet() {
      axios
          .get("http://localhost:8080/api/user/all", config)
          .then((response) => {
            this.users = response.data;
          })
    },
    getUserRole() {
      this.role = this.$store.state.role;
    },
    async deleteUser(username) {
      axios
          .get("http://localhost:8081/api/user/?username=" + username, config)
          .then(response => {
            axios
                .delete("http://localhost:8081/api/user/" + response.data.id, config);
          });
    },
    saveUsernameInLocaleStorage(username) {
      localStorage.setItem('savedUsername', username)
    }
  }
}
</script>

<style>

.myIcon {
  width: 64px;
  height: 64px;
  float: left;
  margin-left: 50%;
}

.myText {
  font-family: 'Roboto', sans-serif;
  font-size: 25px;
  padding: 10px;
  float: left;
}

.myButton {
  margin: 10px;
  float: left;
}
</style>