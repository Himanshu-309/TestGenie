import React from 'react';
import { Link } from 'react-router-dom';

const Home: React.FC = () => {
  return (
    <div className="max-w-4xl mx-auto">
      <div className="text-center mb-12">
        <h1 className="text-4xl font-bold text-gray-900 dark:text-white mb-4">
          Welcome to TestGenie 🧞‍♂️
        </h1>
        <p className="text-xl text-gray-600 dark:text-gray-300">
          Generate test cases for LeetCode-style programming problems with ease
        </p>
      </div>

      <div className="grid md:grid-cols-2 gap-8 mb-12">
        <div className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-lg">
          <h2 className="text-2xl font-semibold text-gray-900 dark:text-white mb-4">
            For Developers
          </h2>
          <ul className="space-y-2 text-gray-600 dark:text-gray-300">
            <li>✓ Generate test cases for any data structure</li>
            <li>✓ Customize constraints and parameters</li>
            <li>✓ Multiple output formats</li>
            <li>✓ Copy to clipboard with one click</li>
          </ul>
        </div>

        <div className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-lg">
          <h2 className="text-2xl font-semibold text-gray-900 dark:text-white mb-4">
            For Interview Prep
          </h2>
          <ul className="space-y-2 text-gray-600 dark:text-gray-300">
            <li>✓ Practice with diverse test cases</li>
            <li>✓ Edge cases and corner scenarios</li>
            <li>✓ Time and space complexity analysis</li>
            <li>✓ Real-world problem simulation</li>
          </ul>
        </div>
      </div>

      <div className="text-center">
        <Link
          to="/generate"
          className="inline-block bg-indigo-600 text-white px-8 py-3 rounded-lg font-medium hover:bg-indigo-700 transition-colors"
        >
          Start Generating
        </Link>
      </div>

      <div className="mt-12 grid md:grid-cols-3 gap-6">
        <div className="text-center">
          <div className="text-3xl font-bold text-indigo-600 dark:text-indigo-400 mb-2">
            100%
          </div>
          <div className="text-gray-600 dark:text-gray-300">Customizable</div>
        </div>
        <div className="text-center">
          <div className="text-3xl font-bold text-indigo-600 dark:text-indigo-400 mb-2">
            3+
          </div>
          <div className="text-gray-600 dark:text-gray-300">Output Formats</div>
        </div>
        <div className="text-center">
          <div className="text-3xl font-bold text-indigo-600 dark:text-indigo-400 mb-2">
            24/7
          </div>
          <div className="text-gray-600 dark:text-gray-300">Available</div>
        </div>
      </div>
    </div>
  );
};

export default Home; 