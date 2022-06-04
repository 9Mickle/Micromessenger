<template>
  <div class="container">
    <div class="messaging">
      <div class="inbox_msg">
        <div class="inbox_people">
          <div class="headind_srch">
            <div class="recent_heading">
              <h4>Чаты</h4>
            </div>
            <div class="srch_bar">
              <div class="stylish-input-group">
                <input type="text" class="search-bar" placeholder="Поиск">
                <span class="input-group-addon">
                <button type="button"> <i class="fa fa-search" aria-hidden="true"></i> </button>
                </span></div>
            </div>
          </div>
          <div class="inbox_chat">
            <div class="chat_list" v-for="chat in chats" :key="chat.id" @click="getMessages(chat.id)">
              <div class="chat_people">
                <div class="chat_img"><img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"></div>
                <div class="chat_ib">
                  <h5>{{ chat.title }} <span class="chat_date">Dec 25</span></h5>
                  <!--                  {{ chat.messages[chat.messages.length - 1].content }}-->
                  <p style="float: left">Последнее сообщение</p>
                  <b-button @click="deleteChat(chat.id)" style="float: right" size="sm">Удалить</b-button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="mesgs" v-if="mod === 'view'">
          <div class="msg_history">
            <div v-for="message in messages" :key="message.id">
              <!--            От кого-то-->
              <div v-if="!(message.senderId === userId)" class="incoming_msg">
                <!--              <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>-->
                <div class="received_msg">
                  <div class="received_withd_msg">
                    <p>{{ message.content }}</p>
                    <span class="time_date"> 11:01 AM    |    June 9</span></div>
                </div>
                <b-button style="float: right; width: 5%" class="bg-danger" size="sm">X</b-button>
              </div>
              <!--            От меня-->
              <div v-if="message.senderId === userId" class="outgoing_msg">
                <div class="sent_msg">
                  <p>{{ message.content }}</p>
                  <span class="time_date"> 11:01 AM    |    June 9</span></div>
              </div>
            </div>
          </div>
          <div class="type_msg">
            <div class="input_msg_write">
              <input type="text" style="width: 85%;" placeholder="Напишите сообщение..." v-model="messageContent"/>
              <b-button class="bg-primary" style="float: right; width: 15%;" size="sm" @click="send">Отправить
              </b-button>
            </div>
          </div>
        </div>
        <div v-if="mod === ''">
          <h1>Выберите чат</h1>
        </div>
      </div>
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
  name: 'chatsGet',
  data() {
    return {
      chats: [],
      messages: [],
      chat_id: Number,
      mod: '',
      currentChatId: null,
      messageContent: '',
      userId: null,
    }
  },
  mounted() {
    this.getAllChats();
  },
  methods: {
    getAllChats() {
      this.username = localStorage.getItem('loginUsername')
      axios
          .get("http://localhost:8080/api/user/?username=" + this.username, config)
          .then((response) => {
            let user_id = response.data.id;
            this.userId = response.data.id;
            axios
                .get("http://localhost:8080/api/chat/all/" + user_id, config)
                .then((response) => {
                  console.log(response.data)
                  this.chats = response.data;
                })
          })
    },
    getMessages(id) {
      this.currentChatId = id;
      this.mod = 'view';
      axios
          .get("http://localhost:8080/api/chat/" + id + "/messages", config)
          .then(response => {
            console.log("ЛОГАЮ ДАТУ")
            console.log(response)
            this.messages = response.data;
          })
    },
    async send() {
      console.log("CURRENT CHAT ID: " + this.currentChatId)
      console.log("CURRENT USER ID: " + this.userId)
      console.log("CURRENT CONTENT: " + this.messageContent)
      const newMessage = {
        "senderId": this.userId,
        "content": this.messageContent,
      }
      await axios
          .post("http://localhost:8080/api/chat/" + this.currentChatId +"/send", newMessage, config)
      await this.getMessages(this.currentChatId)
      this.messageContent = '';
    },
    async deleteChat(id) {
      let username = localStorage.getItem("loginUsername");
      console.log(username)
      await axios
          .delete("http://localhost:8080/api/chat/" + id + "/" + username, config);
      await this.getAllChats();
    }
  }
}
</script>

<style>
.container {
  max-width: 1170px;
  margin: auto;
}

img {
  max-width: 100%;
}

.inbox_people {
  background: #f8f8f8 none repeat scroll 0 0;
  float: left;
  overflow: hidden;
  width: 40%;
  border-right: 1px solid #c4c4c4;
}

.inbox_msg {
  border: 1px solid #c4c4c4;
  clear: both;
  overflow: hidden;
}

.top_spac {
  margin: 20px 0 0;
}


.recent_heading {
  float: left;
  width: 40%;
}

.srch_bar {
  display: inline-block;
  text-align: right;
  width: 60%;
}

.headind_srch {
  padding: 10px 29px 10px 20px;
  overflow: hidden;
  border-bottom: 1px solid #c4c4c4;
}

.recent_heading h4 {
  color: #05728f;
  font-size: 21px;
  margin: auto;
}

.srch_bar input {
  border: 1px solid #cdcdcd;
  border-width: 0 0 1px 0;
  width: 80%;
  padding: 2px 0 4px 6px;
  background: none;
}

.srch_bar .input-group-addon button {
  background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
  border: medium none;
  padding: 0;
  color: #707070;
  font-size: 18px;
}

.srch_bar .input-group-addon {
  margin: 0 0 0 -27px;
}

.chat_ib h5 {
  font-size: 15px;
  color: #464646;
  margin: 0 0 8px 0;
}

.chat_ib h5 span {
  font-size: 13px;
  float: right;
}

.chat_ib p {
  font-size: 14px;
  color: #989898;
  margin: auto
}

.chat_img {
  float: left;
  width: 11%;
}

.chat_ib {
  float: left;
  padding: 0 0 0 15px;
  width: 88%;
}

.chat_people {
  overflow: hidden;
  clear: both;
}

.chat_list {
  border-bottom: 1px solid #c4c4c4;
  margin: 0;
  padding: 18px 16px 10px;
}

.chat_list:hover {
  background-color: #e9ecef;
  cursor: pointer;
}

.inbox_chat {
  height: 550px;
  overflow-y: scroll;
}

.active_chat {
  background: #ebebeb;
}

.incoming_msg_img {
  display: inline-block;
  width: 6%;
}

.received_msg {
  display: inline-block;
  padding: 0 0 0 10px;
  vertical-align: top;
  width: 87%;
}

.received_withd_msg p {
  background: #ebebeb none repeat scroll 0 0;
  border-radius: 3px;
  color: #646464;
  font-size: 14px;
  margin: 0;
  padding: 5px 10px 5px 12px;
  width: 100%;
}

.time_date {
  color: #747474;
  display: block;
  font-size: 12px;
  margin: 8px 0 0;
}

.received_withd_msg {
  width: 57%;
}

.mesgs {
  float: left;
  padding: 30px 15px 0 25px;
  width: 60%;
}

.sent_msg p {
  background: #05728f none repeat scroll 0 0;
  border-radius: 3px;
  font-size: 14px;
  margin: 0;
  color: #fff;
  padding: 5px 10px 5px 12px;
  width: 100%;
}

.outgoing_msg {
  overflow: hidden;
  margin: 26px 0 26px;
}

.sent_msg {
  float: right;
  width: 46%;
}

.input_msg_write input {
  background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
  border: medium none;
  color: #4c4c4c;
  font-size: 15px;
  min-height: 48px;
  width: 100%;
}

.type_msg {
  border-top: 1px solid #c4c4c4;
  position: relative;
}

.msg_send_btn {
  background: #05728f none repeat scroll 0 0;
  border: medium none;
  border-radius: 50%;
  color: #fff;
  cursor: pointer;
  font-size: 17px;
  height: 33px;
  position: absolute;
  right: 0;
  top: 11px;
  width: 33px;
}

.messaging {
  margin-top: 2%;
  padding: 0 0 50px 0;
}

.msg_history {
  height: 516px;
  overflow-y: auto;
}

/* Крестик */
.closebtn {
}

.closebtn:hover {
  color: black;
}

</style>