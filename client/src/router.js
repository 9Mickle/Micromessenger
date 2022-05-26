import Vue from 'vue'
import Router from 'vue-router'
// import firebase from 'firebase/compat/app'
// import 'firebase/compat/auth'
import HomePage from '@/components/HomePage'
import Login from '@/components/auth/Login'
import Registration from '@/components/auth/Registration'
import Users from '@/components/Users'
import Chats from '@/components/Chats'
import UserProfilePage from '@/components/UserProfilePage'
import MyProfile from "@/components/MyProfile";

Vue.use(Router)

const router = new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'Home',
            component: HomePage
        },
        {
            path: '/login',
            name: 'Login',
            component: Login
        },
        {
            path: '/register',
            name: 'Registration',
            component: Registration
        },
        {
            path: '/users',
            name: 'Users',
            component: Users
        },
        {
            path: '/chats',
            name: 'Chats',
            component: Chats
        },
        {
            path: '/profile',
            name: 'UserProfilePage',
            component: UserProfilePage
        },
        {
            path: '/my-profile',
            name: 'MyProfile',
            component: MyProfile
        }
    ]
})

export default router