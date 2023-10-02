import http from 'k6/http';

export const options = {
  stages: [
    {duration: '10s', target: 20},
    {duration: '10s', target: 300},
    {duration: '10s', target: 1000},
    {duration: '10s', target: 2000},
    {duration: '10s', target: 100},
  ],
};

export default function () {
  http.get('http://localhost:8080/performance');
}