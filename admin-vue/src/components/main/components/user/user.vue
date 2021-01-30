<template>
  <div class="user-avator-dropdown">
    <Dropdown @on-click="handleClick">
      <Badge>
        <Avatar :style="{background:'#f60'}" :src="userAvator"/>
      </Badge>
      <Icon :size="18" type="md-arrow-dropdown"></Icon>
      <DropdownMenu slot="list">
        <DropdownItem name="logout">退出登录</DropdownItem>
      </DropdownMenu>
    </Dropdown>
  </div>
</template>

<script>
import './user.less'
import { mapActions } from 'vuex'
export default {
  name: 'User',
  props: {
    userAvator: {
      type: String,
      default: ''
    }
  },
  methods: {
    logout () {
      this.$store.commit('logOut');
      this.$router.push({
        name: 'login'
      })
    },
    message () {

    },
    handleClick (name) {
      switch (name) {
        case 'logout': this.logout()
          break
        case 'message': this.message()
          break
      }
    }
  }
}
</script>
