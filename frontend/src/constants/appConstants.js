export const APP_NAME = 'EarthVoice';

export const STORAGE_KEYS = {
  user: 'ecoblog_user',
  accessToken: 'ecoblog_access_token',
  theme: 'ecoblog_theme',
  recentlyViewed: 'ecoblog_recently_viewed'
};

export const POST_SORT_OPTIONS = [
  { label: 'Newest', value: 'createdAt,desc' },
  { label: 'Most liked', value: 'likeCount,desc' },
  { label: 'Oldest', value: 'createdAt,asc' }
];

export const POST_CATEGORIES = ['Climate', 'Energy', 'Wildlife', 'Zero waste', 'Water', 'Agriculture', 'General'];

export const PAGE_SIZE = 9;
