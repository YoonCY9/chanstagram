import ProfileHeader from './ProfileHeader'
import ProfileInfo from './ProfileInfo'
import ProfileTabs from './ProfileTabs'
import ProfilePosts from './ProfilePosts'
import BackButton from './BackButton'

const ProfilePage: React.FC = () => {
  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      <BackButton />
      <ProfileHeader />
      <ProfileInfo />
      <ProfileTabs />
      <ProfilePosts />
    </div>
  )
}

export default ProfilePage
