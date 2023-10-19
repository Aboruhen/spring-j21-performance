import http from 'k6/http';

export const options = {
  stages: [
    {duration: '10s', target: 100},
    {duration: '10s', target: 500},
    {duration: '10s', target: 500},
    {duration: '10s', target: 5000}
  ],
};

export default function () {
  http.get('http://localhost:8080/files-performance');
}