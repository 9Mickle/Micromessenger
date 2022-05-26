<template>
  <div v-if="this.$store.getters.isAuthenticated" class="myContainer">
    <div class="card">
      <img src="https://cdn-icons-png.flaticon.com/512/921/921071.png" alt="Person" class="card__image">
      <div>
        <p class="card__name">{{ username }}</p>
        <p class="card__name">{{ email }}</p>
      </div>
      <div class="grid-container">

        <!--        <div class="grid-child-posts">-->
        <!--          156 Post-->
        <!--        </div>-->

        <!--        <div class="grid-child-followers">-->
        <!--          1012 Likes-->
        <!--        </div>-->

      </div>
      <button v-b-modal.create-chat class="btn draw-border">Написать</button>
    </div>

    <b-modal id="create-chat" title="Создание чата" hide-footer>
      <b-form-input type="text" placeholder="Введите название чата" v-model="chatTitle"/>
      <div class="mt-2"></div>
      <b-button style="background-color: darkgray; margin: 0 25%" @click="$bvModal.hide('create-chat'); createChat()">
        Create chat
      </b-button>
    </b-modal>
  </div>
</template>

<script>
import axios from "axios";

let token = localStorage.getItem('token');
const config = {
  headers: {Authorization: "Bearer " + token}
};

export default {
  name: "ProfilePage",
  data() {
    return {
      username: '',
      email: '',
      chatTitle: '',
    }
  },
  mounted() {
    this.userGet()
  },
  methods: {
    userGet() {
      delete axios.defaults.headers.common["Authorization"];
      this.username = localStorage.getItem('savedUsername')
      axios
          .get("http://localhost:8081/api/user/?username=" + this.username, config)
          .then((response) => {
            this.email = response.data.email;
            this.password = response.data.password;
          })
    },
    createChat() {
      let loginUsername = localStorage.getItem('loginUsername');
      let savedUsername = localStorage.getItem('savedUsername');
      let usernames = [loginUsername, savedUsername];
      const newChat = {
        'title': this.chatTitle,
        'usernames': usernames
      };
      axios
          .post("http://localhost:8081/api/chat/", newChat, config);
    }
  }
}
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Baloo+Paaji+2:wght@400;500&display=swap");

.myContainer {
  padding: 5%;
  position: relative;
  background-color: #f5f5f5;
  font-family: 'Baloo Paaji 2', cursive;
  min-height: 100vh;
}

.card {
  margin: auto;
  width: 50%;
  height: 100%;
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