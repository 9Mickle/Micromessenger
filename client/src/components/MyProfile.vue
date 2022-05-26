<template>
  <div v-if="this.$store.getters.isAuthenticated" class="myContainer">
    <div class="card">
      <img src="./assets/avatar.png" alt="Person" class="card__image">
      <div v-if="this.mod === 'viewing'">
        <p class="card__name">{{ username }}</p>
        <p class="card__name">{{ email }}</p>
      </div>
      <div v-if="this.mod === 'edit'">
        <p class="card__name">{{ username }}</p>
        <b-form-input type="text" placeholder="Enter email" v-model="email"></b-form-input>
      </div>
      <div class="grid-container">

        <!--        <div class="grid-child-posts">-->
        <!--          156 Post-->
        <!--        </div>-->

        <!--        <div class="grid-child-followers">-->
        <!--          1012 Likes-->
        <!--        </div>-->

      </div>
      <ul class="social-icons">
        <li>
          <button v-if="this.mod === 'viewing'" class="btn draw-border" @click="userEdit">Редактировать</button>
          <button v-if="this.mod === 'edit'" class="btn draw-border" @click="userSave">Сохранить</button>
        </li>
      </ul>
      <button class="btn draw-border">Написать</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

let token = localStorage.getItem('token');
const config = {
  headers: {Authorization: "Bearer " + token}
};

export default {
  name: "MyProfile",
  data() {
    return {
      user_id: Number,
      username: '',
      email: '',
      password: '',
      //viewing, edit
      mod: 'viewing'
    }
  },
  mounted() {
    this.userGet()
  },
  methods: {
    userGet() {
      this.username = localStorage.getItem('loginUsername')
      axios
          .get("http://localhost:8080/api/user/?username=" + this.username, config)
          .then((response) => {
            this.user_id = response.data.id;
            this.email = response.data.email;
            this.password = response.data.password;
          })
    },
    userEdit() {
      this.$data.mod = 'edit';
    },
    userSave() {
      this.$data.mod = 'viewing';
      console.log('id чела: ' + this.user_id);
      console.log('имя: ' + this.username);
      console.log('мыло: ' + this.email);
      let newUser = {
        'id': this.user_id,
        'username': this.username,
        'email': this.email,
        'password': this.password
      };
      console.log(newUser);
      axios
          .put("http://localhost:8080/api/user/", newUser, config)
          .then((response) => {
            console.log(response.data);
          });
    }
  }
}
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Baloo+Paaji+2:wght@400;500&display=swap");

.myContainer {
  background-color: #f5f5f5;
  font-family: 'Baloo Paaji 2', cursive;
  min-height: 100vh;
  padding: 5%;
}

.card {
  margin: auto;
  width: 50%;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-shadow: rgba(0, 0, 0, 0.7);
  color: white;
  background-color: #212529;
  border-radius: 5px;
}

.card__name {
  margin-top: 15px;
  font-size: 1.5em;
}

.card__image {
  height: 160px;
  width: 160px;
  border-radius: 50%;
  border: 5px solid #272133;
  margin-top: 20px;
  box-shadow: 0 10px 50px #f5f5f5;
}


.draw-border {
  box-shadow: inset 0 0 0 4px #f5f5f5;
  color: #e9ecef;
  -webkit-transition: color 0.25s 0.0833333333s;
  transition: color 0.25s 0.0833333333s;
  position: relative;
}

.draw-border::before,
.draw-border::after {
  border: 0 solid transparent;
  box-sizing: border-box;
  content: '';
  pointer-events: none;
  position: absolute;
  width: 0;
  height: 0;
  bottom: 0;
  right: 0;
}

.draw-border::before {
  border-bottom-width: 4px;
  border-left-width: 4px;
}

.draw-border::after {
  border-top-width: 4px;
  border-right-width: 4px;
}

.draw-border:hover {
  color: #fbd4f2;
}

.draw-border:hover::before,
.draw-border:hover::after {
  border-color: #00daff;
  -webkit-transition: border-color 0s, width 0.25s, height 0.25s;
  transition: border-color 0s, width 0.25s, height 0.25s;
  width: 100%;
  height: 100%;
}

.draw-border:hover::before {
  -webkit-transition-delay: 0s, 0s, 0.25s;
  transition-delay: 0s, 0s, 0.25s;
}

.draw-border:hover::after {
  -webkit-transition-delay: 0s, 0.25s, 0s;
  transition-delay: 0s, 0.25s, 0s;
}

.btn {
  background: none;
  border: none;
  cursor: pointer;
  line-height: 2;
  font: 700 1.2rem 'Roboto Slab', sans-serif;
  padding: 0.75em 2em;
  letter-spacing: 0.05rem;
  margin: 1em;
  width: 15rem;
}

.social-icons {
  padding: 0;
  list-style: none;
  margin: 1em;
}
</style>