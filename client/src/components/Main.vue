<template>
  <div class="hello">
    <b-navbar type="dark" variant="light" class="px-4">
      <b-navbar-brand id="nav-brand" class="text-black">MishApp</b-navbar-brand>
      <b-navbar-nav>
        <router-link to="/" class="myLink">Home</router-link>
        <router-link to="/chats" class="myLink" v-if="this.$store.getters.isAuthenticated">Chats
        </router-link>
        <router-link to="/users" class="myLink" v-if="this.$store.getters.isAuthenticated">Users
        </router-link>
      </b-navbar-nav>

      <b-navbar-nav class="ms-auto">
        <router-link to="/register" class="myLink" v-if="!this.$store.getters.isAuthenticated">
          Register
        </router-link>
        <router-link to="/login" class="myLink" v-if="!this.$store.getters.isAuthenticated">
          Login
        </router-link>
        <router-link to="/my-profile" class="myLink" v-if="this.$store.getters.isAuthenticated">
          Profile
        </router-link>
        <a href="#" class="myLink" v-if="this.$store.getters.isAuthenticated"
           v-on:click="logout">Logout</a>
      </b-navbar-nav>
    </b-navbar>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  components: {},
  props: {
    msg: String
  },
  data() {
    return {
      menuClick: "home",
      username: this.$store.getters.getUsername
    }
  },
  methods: {
    logout() {
      this.$store.dispatch('logout');
      this.$router.push('/');
      localStorage.clear();
    }
  }
}
</script>

<style>
</style>